package com.example.backend.util.exceptions

class BadRequestException(override val message: String?) : Exception(message)