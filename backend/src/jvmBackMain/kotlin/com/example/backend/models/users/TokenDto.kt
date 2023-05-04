package com.example.backend.models.users

data class TokenDTO(
    val accessToken: String,
    val refreshToken: String
)