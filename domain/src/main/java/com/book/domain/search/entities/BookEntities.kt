package com.book.domain.search.entities

import com.google.gson.annotations.SerializedName

data class BookEntities(
    val documents: ArrayList<Document>,
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

}