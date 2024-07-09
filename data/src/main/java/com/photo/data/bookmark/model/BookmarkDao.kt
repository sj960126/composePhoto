package com.photo.data.bookmark.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    suspend fun getAll(): List<BookmarkEntity>
    @Query("SELECT title FROM bookmarks")
    suspend fun getAllTitles(): List<String>
    @Insert
    suspend fun insert(bookmark: BookmarkEntity)
    @Query("DELETE FROM bookmarks WHERE title = :title")
    suspend fun delete(title: String)

}