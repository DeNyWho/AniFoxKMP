package com.example.backend.repository.user

import com.example.backend.jpa.user.Role
import com.example.backend.jpa.user.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository: JpaRepository<Role, String> {
    fun findByName(name: RoleName): Optional<Role>
}