package com.example.backend.util

import java.util.*

fun translit(str: String): String {
    val charMap = mapOf(
        'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'ґ' to "g", 'д' to "d", 'е' to "e",
        'ё' to "yo", 'є' to "ie", 'ж' to "zh", 'з' to "z", 'и' to "i", 'і' to "i", 'ї' to "i", 'й' to "i",
        'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r",
        'с' to "s", 'т' to "t", 'у' to "u", 'ф' to "f", 'х' to "kh", 'ц' to "ts", 'ч' to "ch",
        'ш' to "sh", 'щ' to "shch", 'ы' to "y", 'ь' to "", 'э' to "e", 'ю' to "iu", 'я' to "ia",
        'А' to "A", 'Б' to "B", 'В' to "V", 'Г' to "G", 'Ґ' to "G", 'Д' to "D", 'Е' to "E",
        'Ё' to "Yo", 'Є' to "Ye", 'Ж' to "Zh", 'З' to "Z", 'И' to "I", 'І' to "I", 'Ї' to "Yi", 'Й' to "Y",
        'К' to "K", 'Л' to "L", 'М' to "M", 'Н' to "N", 'О' to "O", 'П' to "P", 'Р' to "R",
        'С' to "S", 'Т' to "T", 'У' to "U", 'Ф' to "F", 'Х' to "Kh", 'Ц' to "Ts", 'Ч' to "Ch",
        'Ш' to "Sh", 'Щ' to "Shch", 'Ы' to "Y", 'Ь' to "", 'Э' to "E", 'Ю' to "Yu", 'Я' to "Ya"
    )

    return str.lowercase(Locale.getDefault())
        .map { charMap[it] ?: if (it.isLetterOrDigit()) it.toString() else "-" }
        .joinToString("")
        .dropLastWhile { it == '-' }
}