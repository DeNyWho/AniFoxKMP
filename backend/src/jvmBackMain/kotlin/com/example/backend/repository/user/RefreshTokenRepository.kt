package com.example.backend.repository.user

import com.example.backend.jpa.user.RefreshToken
import com.example.backend.jpa.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun findByToken(token: String): RefreshToken?
    fun deleteByToken(token: String)
    fun findByUser(user: User): RefreshToken?
}