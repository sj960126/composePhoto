package com.book.data.bookmark.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:08 AM
 */

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