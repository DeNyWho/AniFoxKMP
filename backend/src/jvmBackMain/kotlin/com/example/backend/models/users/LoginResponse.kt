package com.example.backend.models.users

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)