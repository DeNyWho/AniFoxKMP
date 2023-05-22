package com.example.common.util

object ExtractCookie {
    fun extractCookieValues(cookieString: String): Map<String, String> {
        val cookies = cookieString.split("; ") // Разделяем строку по точке с запятой и пробелу
        val cookieMap = mutableMapOf<String, String>()

        for (cookie in cookies) {
            val parts = cookie.split("=") // Разделяем каждую часть cookie по знаку "="
            if (parts.size == 2) {
                val key = parts[0]
                val value = parts[1]
                cookieMap[key] = value
            }
        }

        return cookieMap
    }
}