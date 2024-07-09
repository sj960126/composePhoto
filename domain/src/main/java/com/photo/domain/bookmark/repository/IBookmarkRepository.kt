package com.photo.domain.bookmark.repository

import com.photo.domain.common.entities.BookEntities

interface IBookmarkRepository {
    suspend fun getBookmarks(): List<BookEntities.Document>
    suspend fun addBookmark(document: BookEntities.Document)
    suspend fun removeBookmark(title: String)

}