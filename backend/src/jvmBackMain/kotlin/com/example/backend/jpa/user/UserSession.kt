package com.example.backend.jpa.user

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "users", name = "user_sessions")
data class UserSession(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val sessionId: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = User(),

    @Column(nullable = false)
    val creationTime: LocalDateTime = LocalDateTime.now(),

    var lastAccessTime: LocalDateTime = LocalDateTime.now()
)