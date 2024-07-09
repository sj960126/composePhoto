package com.photo.domain.search.entities

data class SearchPhotoRequest(
    val query : String,
    val sort : String? = null,
    val page : Int? = null,
    val size :Int? = null,
)
