package com.book.data.bookmark.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:09 AM
 */
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