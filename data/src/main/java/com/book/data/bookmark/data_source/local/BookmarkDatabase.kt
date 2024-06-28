package com.book.data.bookmark.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.book.data.bookmark.model.BookmarkDao
import com.book.data.bookmark.model.BookmarkEntity

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:22 AM
 */
@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}