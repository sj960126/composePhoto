package com.book.data.bookmark.data_source.local

import com.book.data.bookmark.model.BookmarkDao
import com.book.data.bookmark.model.BookmarkEntity
import javax.inject.Inject

class BookmarkLocalDataSource @Inject constructor(private val bookmarkDao: BookmarkDao)  {

    suspend fun getBookmarks(): List<BookmarkEntity> = bookmarkDao.getAll()
    suspend fun getAllTitles(): List<String> = bookmarkDao.getAllTitles()
    suspend fun addBookmark(bookmarkEntity: BookmarkEntity) = bookmarkDao.insert(bookmarkEntity)
    suspend fun removeBookmark(title: String) = bookmarkDao.delete(title)

}
