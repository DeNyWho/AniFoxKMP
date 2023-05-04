package com.example.backend.jpa.user

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "oauth_tokens", schema = "users")
data class OAuthToken(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    var token: String = "",

    @Column(nullable = false)
    var expiration: Instant = Instant.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tokenType: TokenType = TokenType.ACCESS,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User = User(),
)

enum class TokenType {
    ACCESS, REFRESH
}