package com.photo.data.bookmark.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val authors: String?,
    val contents: String?,
    val datetime: String?,
    val isbn: String?,
    val price: Int?,
    val publisher: String?,
    val salePrice: Int?,
    val status: String?,
    val thumbnail: String?,
    val title: String?,
    val translators: String?,
    val url: String?,
    val isBookmark :Boolean
)