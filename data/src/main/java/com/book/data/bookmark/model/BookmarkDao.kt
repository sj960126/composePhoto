package com.book.data.bookmark.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:09 AM
 */
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    suspend fun getAll(): List<BookmarkEntity>

    @Insert
    suspend fun insert(bookmark: BookmarkEntity)

    @Delete
    suspend fun delete(bookmark: BookmarkEntity)
}