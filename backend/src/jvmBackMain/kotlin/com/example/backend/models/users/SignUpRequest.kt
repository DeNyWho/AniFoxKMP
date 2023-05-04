package com.example.backend.models.users

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)