package com.book.data.bookmark.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.book.data.bookmark.model.BookmarkDao
import com.book.data.bookmark.model.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 2, exportSchema = false)
abstract class BookmarkDatabase : RoomDatabase() {
    companion object {
        // 북마크 기능 추가로 Room MIGRATION
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE bookmarks ADD COLUMN isBookmark INTEGER NOT NULL DEFAULT 1")
            }
        }
    }
    abstract fun bookmarkDao(): BookmarkDao
}