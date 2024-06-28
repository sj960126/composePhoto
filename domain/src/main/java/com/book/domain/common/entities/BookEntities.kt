package com.book.domain.common.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BookEntities(
    val documents: ArrayList<Document>,
) {
    data class Document(
        val authors: ArrayList<String>?,
        val contents: String?,
        val datetime: String?,
        val isbn: String?,
        val price: Int?,
        val publisher: String?,
        @SerializedName("sale_price")
        val salePrice: Int?,
        val status: String?,
        val thumbnail: String?,
        val title: String?,
        val translators: ArrayList<String>?,
        val url: String?
    )  : Serializable

}