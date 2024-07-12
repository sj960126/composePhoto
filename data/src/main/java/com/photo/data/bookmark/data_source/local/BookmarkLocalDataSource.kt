package com.photo.data.bookmark.data_source.local

import com.photo.data.bookmark.model.BookmarkDao
import com.photo.data.bookmark.model.BookmarkEntity
import javax.inject.Inject

class BookmarkLocalDataSource @Inject constructor(private val bookmarkDao: BookmarkDao)  {
    suspend fun fetchAllBookmarks(): List<BookmarkEntity> = bookmarkDao.getAll()
    suspend fun fetchAllBookmarkThumbnail(): List<String> = bookmarkDao.getAllThumbnailUrl()
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity) = bookmarkDao.insert(bookmarkEntity)
    suspend fun removeBookmark(title: String) = bookmarkDao.delete(title)
    suspend fun clearAllBookmarks() = bookmarkDao.deleteAll()
    suspend fun fetchBookmarksByKeyword(keyword : String) = bookmarkDao.getByCollection(keyword)
}
