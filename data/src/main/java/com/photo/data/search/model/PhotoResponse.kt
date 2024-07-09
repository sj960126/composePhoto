package com.photo.data.search.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    val documents: List<Document>,
    val meta: Meta
) {
    data class Meta(
        val is_end: Boolean,
        val pageable_count: Int,
        val total_count: Int
        )

    data class Document(
        val collection : String?,
        @SerializedName("thumbnail_url")
        val thumbnailUrl : String?,
        @SerializedName("image_url")
        val imageUrl : String?,
        val width : Int?,
        val height : Int?,
        @SerializedName("display_sitename")
        val displaySitename : String?,
        @SerializedName("doc_url")
        val docUrl : String?,
        val datetime : String?
        )
}