package com.example.backend.models.users

import org.springframework.security.core.AuthenticationException

class MyAuthenticationException(message: String) : AuthenticationException(message) {
    override fun getLocalizedMessage(): String? {
        return message
    }
}