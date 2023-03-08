package com.example.common.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.MangaRepository
import com.example.common.models.mangaResponse.light.MangaLight
import org.koin.core.component.KoinComponent
import java.io.IOException

class MangaPagingSource (
    private val mangaRepository: MangaRepository,
    private val query: String,
    private val pageSize: Int,
    private val order: String?,
    private val status: String?,
    private val genres: List<String>?
): KoinComponent, PagingSource<Int, MangaLight>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaLight> {
//        val currentPage = params.key ?: 0
//        return try {
//            val response = mangaRepository.getManga(pageNum = currentPage, pageSize = pageSize, searchQuery = query, order = order, genres = genres, status = status).data
//            val endOfPaginationReached = response?.data?.isEmpty()
//            if (response?.data?.isNotEmpty() == true) {
//                LoadResult.Page(
//                    data = response.data!!,
//                    prevKey = if (currentPage == 0) null else currentPage - 1,
//                    nextKey = if (endOfPaginationReached == true) null else currentPage + 1
//                )
//            } else {
//                LoadResult.Page(
//                    data = emptyList(),
//                    prevKey = null,
//                    nextKey = null
//                )
//            }
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
//        return /*if (invalid) 1 else state.anchorPosition?.let {
//            val anchorPage = state.closestPageToPosition(it)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }*/ 1
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaLight> {
        return try {
            if (query.isEmpty()) return LoadResult.Invalid()
            val position = params.key ?: 0
            return when (val result = mangaRepository.getManga(pageNum = position, pageSize = pageSize, searchQuery = query, order = order, genres = genres, status = status)) {
                is Resource.Success -> {
                    LoadResult.Page(
                        data = result.data?.data!!,
                        nextKey = if (result.data.data.isNullOrEmpty()) null else position.inc(),
                        prevKey = if (position == 0) null else position.dec()
                    )
                }
                else -> {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MangaLight>): Int {
        return 0
    }

}