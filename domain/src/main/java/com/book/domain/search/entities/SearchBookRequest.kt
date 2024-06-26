package com.book.domain.search.entities

data class SearchBookRequest(
    val query : String,
    val sort : String? = null,
    val page : Int? = null,
    val size :Int? = null,
    val target : String? = null
)
