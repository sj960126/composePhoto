package com.photo.data.bookmark.repository

import com.photo.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.photo.data.bookmark.mapper.BookmarkMapper.toBookMarkEntities
import com.photo.data.bookmark.mapper.BookmarkMapper.toPhotoEntities
import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.PhotoEntities
import javax.inject.Inject

class BookmarkRepositoryImp @Inject constructor(private val localDataSource: BookmarkLocalDataSource) : IBookmarkRepository {
    override suspend fun getBookmarks(): List<PhotoEntities.Document> = localDataSource.getBookmarks().map { it.toPhotoEntities() }
    override suspend fun addBookmark(document: PhotoEntities.Document) = localDataSource.addBookmark(document.toBookMarkEntities())
    override suspend fun removeBookmark(title: String) =  localDataSource.removeBookmark(title)
    override suspend fun clearBookmark()  = localDataSource.clearBookmark()
    override suspend fun getByCollection(keyword: String) : List<PhotoEntities.Document> = localDataSource.getByCollection(keyword = keyword).map { it.toPhotoEntities() }
}