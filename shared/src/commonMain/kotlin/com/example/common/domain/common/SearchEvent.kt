package com.example.common.domain.common

sealed class SearchEvent {
    class SearchFirstPage(val query: String): SearchEvent()
    class SearchNextPage(val query: String): SearchEvent()
}