package com.book.data.bookmark.repository

import com.book.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.book.data.bookmark.mapper.BookmarkMapper.toEntity
import com.book.data.bookmark.mapper.BookmarkMapper.toDomain
import com.book.domain.bookmark.repository.IBookmarkRepository
import com.book.domain.common.entities.BookEntities
import javax.inject.Inject

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:40 AM
 */
class BookmarkRepositoryImp @Inject constructor(private val localDataSource: BookmarkLocalDataSource) : IBookmarkRepository {
    override suspend fun getBookmarks(): List<BookEntities.Document> = localDataSource.getBookmarks().map { it.toDomain() }
    override suspend fun addBookmark(document: BookEntities.Document) = localDataSource.addBookmark(document.toEntity())
    override suspend fun removeBookmark(title: String) =  localDataSource.removeBookmark(title)

}