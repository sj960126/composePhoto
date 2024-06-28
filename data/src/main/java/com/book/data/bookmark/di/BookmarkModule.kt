package com.book.data.bookmark.di

import android.content.Context
import androidx.room.Room
import com.book.data.bookmark.data_source.local.BookmarkDatabase
import com.book.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.book.data.bookmark.model.BookmarkDao
import com.book.data.bookmark.repository.BookmarkRepositoryImp
import com.book.domain.bookmark.repository.IBookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:31 AM
 */
@Module
@InstallIn(SingletonComponent::class)
object BookmarkModule {

    private const val DATABASE_NAME ="bookmark_database"

    @Provides
    fun provideBookmarkRepository(bookmarkLocalDataSource: BookmarkLocalDataSource): IBookmarkRepository =BookmarkRepositoryImp(bookmarkLocalDataSource)

    @Provides
    fun provideBookmarkLocalDataSource(bookmarkDao: BookmarkDao):  BookmarkLocalDataSource  = BookmarkLocalDataSource(bookmarkDao)

    @Provides
    fun provideBookmarkDao(database: BookmarkDatabase): BookmarkDao = database.bookmarkDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): BookmarkDatabase = Room.databaseBuilder(appContext, BookmarkDatabase::class.java, DATABASE_NAME).build()

}