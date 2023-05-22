package com.example.android.data.repository

//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.common.data.repository.MangaRepository
//import com.example.common.models.common.ContentLight
//import com.example.common.models.mangaResponse.light.toContentLight
//
//class MangaPagingSource(
//    private val repository: MangaRepository,
//    private val query: String? = null,
//    private val pageSize: Int = 24,
//    private val order: String? = null,
//    private val status: String? = null,
//    private val genres: List<String>? = null
//): PagingSource<Int, ContentLight>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContentLight> {
//        val currentPage = params.key ?: 0
//        return try {
//            val response = repository.getManga(
//                searchQuery = query,
//                pageSize = pageSize,
//                order = order,
//                status = status,
//                genres = genres,
//                pageNum = currentPage
//            )
//            val endOfPaginationReached = response.data == null
//            if (response.data?.data?.isNotEmpty() == true) {
//                LoadResult.Page(
//                    data = response.data!!.data!!.map {
//                        it.toContentLight()
//                    },
//                    prevKey = if (currentPage == 0) null else currentPage - 1,
//                    nextKey = if (endOfPaginationReached) null else currentPage + 1
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
//
//    override fun getRefreshKey(state: PagingState<Int, ContentLight>): Int? {
//        return state.anchorPosition
//    }
//
//}