package com.example.backend.jpa.user

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val token: String = "",

    @Column(nullable = false)
    val expiration: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User = User()
) {
    val isValid: Boolean
        get() = expiration.isAfter(Instant.now())
}