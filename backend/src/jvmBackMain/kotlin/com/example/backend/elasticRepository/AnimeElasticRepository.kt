package com.example.backend.elasticRepository

//import com.example.backend.jpa.anime.AnimeTable
//import org.springframework.data.domain.Pageable
//import org.springframework.data.elasticsearch.annotations.Query
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
//import org.springframework.stereotype.Repository
//import java.util.*
//
//@Repository
//interface AnimeElasticRepository : ElasticsearchRepository<AnimeTable, String> {
//
//    @Query("""
//        {
//        "bool": {
//            "must": [
//                {"match": {"status": {"query": "?0"}}},
//                {"match": {"ratingMpa": {"query": "?1"}}},
//                {"match": {"season": {"query": "?2"}}},
//                {"match": {"type": {"query": "?3"}}},
//                {"match": {"minimalAge": {"query": "?4"}}},
//                {"match": {"year": {"query": "?5"}}}
//            ],
//            "should": [
//                {"match": {"otherTitles": {"query": "?6", "fuzziness": "AUTO", "boost": 2}}}
//            ],
//            "minimum_should_match": 1
//        }
//    }
//    """)
//    fun findAnime(
//        pageable: Pageable,
//        status: String?,
//        searchQuery: String?,
//        ratingMpa: String?,
//        season: String?,
//        minimalAge: Int?,
//        type: String?,
//        year: Int?
//    ): List<AnimeTable>
//
//}