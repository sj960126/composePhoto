package com.photo.domain.bookmark.repository

import com.photo.domain.common.entities.PhotoEntities

interface IBookmarkRepository {
    suspend fun getBookmarks(): List<PhotoEntities.Document>
    suspend fun addBookmark(document: PhotoEntities.Document)
    suspend fun removeBookmark(title: String)
    suspend fun clearBookmark()
    suspend fun getByCollection(keyword: String) : List<PhotoEntities.Document>
}