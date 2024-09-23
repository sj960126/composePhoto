package com.photo.data.bookmark.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    suspend fun getAll(): List<BookmarkEntity>
    @Query("SELECT thumbnailUrl FROM bookmark")
    suspend fun getAllThumbnailUrl(): List<String>
    @Insert
    suspend fun insert(bookmark: BookmarkEntity)
    @Query("DELETE FROM bookmark WHERE thumbnailUrl = :thumbnailUrl")
    suspend fun delete(thumbnailUrl: String)
    @Query("DELETE FROM bookmark")
    suspend fun deleteAll()
    @Query("SELECT * FROM bookmark WHERE collection = :collection")
    suspend fun getByCollection(collection: String): List<BookmarkEntity>
}