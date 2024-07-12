package com.photo.domain.bookmark.repository

import com.photo.domain.common.entities.PhotoEntities

interface IBookmarkRepository {
    suspend fun fetchAllBookmarks(): List<PhotoEntities.Document>
    suspend fun insertBookmark(document: PhotoEntities.Document)
    suspend fun removeBookmark(title: String)
    suspend fun clearAllBookmarks()
    suspend fun fetchBookmarksByKeyword(keyword: String) : List<PhotoEntities.Document>
}