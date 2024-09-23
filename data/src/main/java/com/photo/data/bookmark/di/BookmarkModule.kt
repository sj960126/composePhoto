package com.photo.data.bookmark.di

import android.content.Context
import androidx.room.Room
import com.photo.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.photo.data.bookmark.data_source.local.BookmarkLocalDatabase
import com.photo.data.bookmark.model.BookmarkDao
import com.photo.data.bookmark.repository.BookmarkRepositoryImp
import com.photo.domain.bookmark.repository.IBookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BookmarkModule {

    private const val DATABASE_NAME ="bookmark_local_database"

    @Provides
    fun provideBookmarkRepository(bookmarkLocalDataSource: BookmarkLocalDataSource): IBookmarkRepository = BookmarkRepositoryImp(bookmarkLocalDataSource)

    @Provides
    fun provideBookmarkLocalDataSource(bookmarkDao: BookmarkDao):  BookmarkLocalDataSource  = BookmarkLocalDataSource(bookmarkDao)

    @Provides
    fun provideBookmarkDao(database: BookmarkLocalDatabase): BookmarkDao = database.bookmarkDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): BookmarkLocalDatabase = Room.databaseBuilder(appContext, BookmarkLocalDatabase::class.java, DATABASE_NAME).build()

}