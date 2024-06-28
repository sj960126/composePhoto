package com.book.data.bookmark.data_source.local

import com.book.data.bookmark.model.BookmarkDao
import com.book.data.bookmark.model.BookmarkEntity
import javax.inject.Inject

class BookmarkLocalDataSource @Inject constructor(private val bookmarkDao: BookmarkDao)  {

     suspend fun getBookmarks(): List<BookmarkEntity> {
        return bookmarkDao.getAll()
    }

     suspend fun addBookmark(bookmarkEntity: BookmarkEntity) {
        bookmarkDao.insert(bookmarkEntity)
    }

     suspend fun removeBookmark(bookmarkEntity: BookmarkEntity) {
        bookmarkDao.delete(bookmarkEntity)
    }
}
