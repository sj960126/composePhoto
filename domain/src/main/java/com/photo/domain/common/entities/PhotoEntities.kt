package com.photo.domain.common.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PhotoEntities(
    val documents: ArrayList<Document>,
) {
    data class Document(
        val collection : String?,
        val thumbnailUrl : String?,
        val imageUrl : String?,
        val displaySitename : String?,
        val datetime : String?,
        var isBookMark : Boolean = false
    )  : Serializable

}