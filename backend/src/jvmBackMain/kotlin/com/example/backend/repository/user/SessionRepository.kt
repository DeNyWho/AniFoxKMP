package com.example.backend.repository.user

import com.example.backend.jpa.user.User
import com.example.backend.jpa.user.UserSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<UserSession, String> {
    fun findByUser(user: User): List<UserSession>
    fun findBySessionId(sessionId: String): UserSession
}