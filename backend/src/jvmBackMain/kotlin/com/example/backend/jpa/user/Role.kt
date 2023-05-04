package com.example.backend.jpa.user

import java.util.*
import java.util.stream.Stream
import javax.persistence.*

@Entity
@Table(name = "roles", schema = "users")
data class Role(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val name: RoleName = RoleName.ROLE_USER
)

enum class RoleName {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MODERATOR;
    companion object {
        fun stream(): Stream<RoleName> {
            return Arrays.stream(values())
        }
    }
}

