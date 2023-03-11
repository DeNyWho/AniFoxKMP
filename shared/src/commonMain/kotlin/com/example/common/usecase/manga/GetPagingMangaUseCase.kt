package com.example.common.usecase.manga

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.paging.MangaPagingSource
import com.example.common.data.repository.MangaRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.mangaResponse.light.MangaLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPagingMangaUseCase(private val repository: MangaRepository) {
    operator fun invoke(
        order: String? = null,
        pageNum: Int = 0,
        pageSize: Int = 24,
        status: String? = null,
        genres: List<String>? = null,
        searchQuery: String? = null
    ): Flow<PagingData<MangaLight>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                MangaPagingSource(repository = repository, query = searchQuery, order = order, status = status, genres = genres)
            }
        ).flow
    }
}