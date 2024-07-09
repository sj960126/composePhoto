package com.photo.data.bookmark.repository

import com.photo.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.photo.data.bookmark.mapper.BookmarkMapper.toEntity
import com.photo.data.bookmark.mapper.BookmarkMapper.toDomain
import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.BookEntities
import javax.inject.Inject

class BookmarkRepositoryImp @Inject constructor(private val localDataSource: BookmarkLocalDataSource) : IBookmarkRepository {
    override suspend fun getBookmarks(): List<BookEntities.Document> = localDataSource.getBookmarks().map { it.toDomain() }
    override suspend fun addBookmark(document: BookEntities.Document) = localDataSource.addBookmark(document.toEntity())
    override suspend fun removeBookmark(title: String) =  localDataSource.removeBookmark(title)

}