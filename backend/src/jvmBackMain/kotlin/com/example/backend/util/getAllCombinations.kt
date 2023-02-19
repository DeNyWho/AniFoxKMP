package com.example.backend.util

import kotlin.math.pow

fun getAllCombinationsMangaGenre(elements: List<String>): List<List<String>> {
    val combinationList: MutableList<List<String>> = ArrayList()
    var i: Long = 1
    while (i < 2.0.pow(elements.size.toDouble())) {
        val list: MutableList<String> = ArrayList()
        for (j in elements.indices) {
            if (i and 2.0.pow(j.toDouble()).toLong() > 0) {
                list.add(elements[j])
            }
        }
        combinationList.add(list)
        i++
    }
    println(combinationList.size)
    return combinationList
}