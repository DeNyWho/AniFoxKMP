package com.example.android.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.data.repository.MangaPagingSource
import com.example.common.data.repository.MangaRepository
import com.example.common.models.common.ContentLight
import kotlinx.coroutines.flow.Flow

class GetPagingMangaUseCase(private val repository: MangaRepository) {
    operator fun invoke(
        order: String? = null,
        pageNum: Int = 0,
        pageSize: Int = 24,
        status: String? = null,
        genres: List<String>? = null,
        searchQuery: String? = null
    ): Flow<PagingData<ContentLight>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, prefetchDistance = 1, enablePlaceholders = false, initialLoadSize = pageSize),
            pagingSourceFactory = {
                MangaPagingSource(repository = repository, query = searchQuery, order = order, status = status, genres = genres)
            }
        ).flow
    }
}