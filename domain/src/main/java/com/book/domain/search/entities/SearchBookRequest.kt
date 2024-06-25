package com.book.domain.search.entities

data class SearchBookRequest(
    val query : String,
    val sort : String?,
    val page : Int?,
    val size :Int?,
    val target : String?
)
