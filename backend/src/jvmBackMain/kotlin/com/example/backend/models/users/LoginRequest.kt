package com.example.backend.models.users

data class LoginRequest(
    val email: String,
    val password: String
)