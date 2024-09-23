package com.photo.data.bookmark.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
internal data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val collection : String?,
    val thumbnailUrl : String?,
    val imageUrl : String?,
    val displaySitename : String?,
    val datetime : String?,
    var isBookMark : Boolean = false,
)