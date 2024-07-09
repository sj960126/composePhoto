package com.photo.data.bookmark.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    suspend fun getAll(): List<BookmarkEntity>
    @Query("SELECT thumbnailUrl FROM bookmark")
    suspend fun getAllThumbnailUrl(): List<String>
    @Insert
    suspend fun insert(bookmark: BookmarkEntity)
    @Query("DELETE FROM bookmark WHERE thumbnailUrl = :thumbnailUrl")
    suspend fun delete(thumbnailUrl: String)

}