package com.example.backend.util.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    @Value("\${jwt.expiration.access}")
    private val accessExpiration: Long,
    @Value("\${jwt.expiration.refresh}")
    private val refreshExpiration: Long
) {
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    val secret: ByteArray = key.encoded

    fun generateAccessToken(userDetails: UserDetails): String {
        val claims = Jwts.claims().setSubject(userDetails.username)
        claims["roles"] = userDetails.authorities.map { it.authority }
        val now = Date()
        val validity = Date(now.time + accessExpiration * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun generateRefreshToken(): String {
        val now = Date()
        val validity = Date(now.time + refreshExpiration * 1000)
        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun getUsernameFromToken(token: String): String {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getExpirationDateFromToken(token: String): Date {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
            .expiration
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }
}