package com.book.data.search.model

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val documents: ArrayList<Document>,
    val meta: Meta
) {
    data class Document(
        val authors: ArrayList<String>,
        val contents: String,
        val datetime: String,
        val isbn: String,
        val price: Int,
        val publisher: String,
        @SerializedName("sale_price")
        val salePrice: Int,
        val status: String,
        val thumbnail: String,
        val title: String,
        val translators: ArrayList<String>,
        val url: String
    )

    data class Meta(
        @SerializedName("is_end")
        val isEnd: Boolean,
        @SerializedName("pageable_count")
        val pageableCount: Int,
        @SerializedName("total_count")
        val totalCount: Int
    )
}