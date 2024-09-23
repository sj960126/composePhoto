package com.photo.data.bookmark.repository

import com.photo.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.photo.data.bookmark.mapper.BookmarkMapper.toBookMarkEntities
import com.photo.data.bookmark.mapper.BookmarkMapper.toPhotoEntities
import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.PhotoEntities
import javax.inject.Inject

internal class BookmarkRepositoryImp @Inject constructor(private val localDataSource: BookmarkLocalDataSource) : IBookmarkRepository {
    override suspend fun fetchAllBookmarks(): List<PhotoEntities.Document> = localDataSource.fetchAllBookmarks().map { it.toPhotoEntities() }
    override suspend fun insertBookmark(document: PhotoEntities.Document) = localDataSource.insertBookmark(document.toBookMarkEntities())
    override suspend fun removeBookmark(title: String) =  localDataSource.removeBookmark(title)
    override suspend fun clearAllBookmarks()  = localDataSource.clearAllBookmarks()
    override suspend fun fetchBookmarksByKeyword(keyword: String) : List<PhotoEntities.Document> = localDataSource.fetchBookmarksByKeyword(keyword = keyword).map { it.toPhotoEntities() }
}