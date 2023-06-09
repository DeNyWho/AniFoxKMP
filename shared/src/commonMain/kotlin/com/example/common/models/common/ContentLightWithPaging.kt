package com.example.common.models.common

import kotlinx.serialization.Serializable

@Serializable
data class ContentLightWithPaging(
    val data: List<ContentLight> = listOf(),
    val pagination: Pagination = Pagination.Default
)