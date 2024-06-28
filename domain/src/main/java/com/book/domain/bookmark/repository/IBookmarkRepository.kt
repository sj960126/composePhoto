package com.book.domain.bookmark.repository

import com.book.domain.common.entities.BookEntities

interface IBookmarkRepository {
    suspend fun getBookmarks(): List<BookEntities.Document>
    suspend fun addBookmark(document: BookEntities.Document)
    suspend fun removeBookmark(document: BookEntities.Document)
}