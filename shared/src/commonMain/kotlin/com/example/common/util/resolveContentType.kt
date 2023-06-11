package com.example.common.util

import com.example.common.models.animeResponse.AnimeRelatedLight
import com.example.common.models.animeResponse.detail.AnimeDetail
import com.example.common.models.animeResponse.detail.toContentDetail
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.animeResponse.light.toContentLight
import com.example.common.models.animeResponse.toContentLight
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.detail.toContentDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.mangaResponse.light.toContentLight

inline fun <reified T> resolveContentType(data: Any?): T {
    return when (data) {
        is AnimeDetail -> data.toContentDetail() as T
        is MangaDetail -> data.toContentDetail() as T
        is AnimeLight -> data.toContentLight() as T
        is MangaLight -> data.toContentLight() as T
        is AnimeRelatedLight -> data.toContentLight() as T
        else -> data as T
    }
}