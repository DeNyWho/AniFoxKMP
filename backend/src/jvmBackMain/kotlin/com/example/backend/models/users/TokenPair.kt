package com.example.backend.models.users

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)