package com.book.data_core.base

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code : String? = null,
    @SerializedName("message")
    val message : String? = null,
    @SerializedName("data")
    val data : Any?= null
) : java.io.Serializable