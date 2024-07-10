package com.photo.data.bookmark.data_source.local

import com.photo.data.bookmark.model.BookmarkDao
import com.photo.data.bookmark.model.BookmarkEntity
import javax.inject.Inject

class BookmarkLocalDataSource @Inject constructor(private val bookmarkDao: BookmarkDao)  {

    suspend fun getBookmarks(): List<BookmarkEntity> = bookmarkDao.getAll()
    suspend fun getAllTitles(): List<String> = bookmarkDao.getAllThumbnailUrl()
    suspend fun addBookmark(bookmarkEntity: BookmarkEntity) = bookmarkDao.insert(bookmarkEntity)
    suspend fun removeBookmark(title: String) = bookmarkDao.delete(title)
    suspend fun clearBookmark() = bookmarkDao.deleteAll()
    suspend fun getByCollection(keyword : String) = bookmarkDao.getByCollection(keyword)
}
