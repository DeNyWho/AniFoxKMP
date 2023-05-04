package com.example.backend.util

import com.example.backend.repository.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = if(userRepository.findByEmail(username).isPresent) userRepository.findByEmail(username).get() else throw UsernameNotFoundException("User not found")
        return User(
            user.email,
            user.password,
            user.roles.map { SimpleGrantedAuthority(it.name.name) }
        )
    }
}