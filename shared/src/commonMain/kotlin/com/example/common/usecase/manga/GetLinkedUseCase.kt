package com.example.common.usecase.manga
//
//import com.example.common.core.wrapper.Event
//import com.example.common.core.wrapper.Resource
//import com.example.common.data.repository.MangaRepository
//import com.example.common.domain.common.StateListWrapper
//import com.example.common.models.common.ContentLight
//import com.example.common.models.mangaResponse.light.toContentLight
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//
//class GetLinkedUseCase(private val repository: MangaRepository){
//    operator fun invoke(id: String): Flow<StateListWrapper<ContentLight>>{
//        return flow {
//            emit(StateListWrapper.loading())
//
//            val state = when (val res = repository.getMangaLinked(id)) {
//                is Resource.Success -> {
//                    val data = res.data?.data?.map {
//                        it.toContentLight()
//                    } .orEmpty()
//
//                    StateListWrapper(data)
//                }
//                is Resource.Error -> {
//                    StateListWrapper(error = Event(res.message))
//                }
//                is Resource.Loading -> StateListWrapper.loading()
//            }
//            emit(state)
//        }.flowOn(Dispatchers.IO)
//    }
//}