package com.photo.data.bookmark.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.photo.data.bookmark.model.BookmarkDao
import com.photo.data.bookmark.model.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class BookmarkLocalDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}