package com.example.backend.repository.user

import com.example.backend.jpa.user.OAuthToken
import com.example.backend.jpa.user.TokenType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<OAuthToken, String> {
    fun findByTokenAndTokenType(token: String, tokenType: TokenType): OAuthToken?
}