package com.photo.data.bookmark.mapper

import com.photo.data.bookmark.mapper.BookmarkMapper.toPhotoEntities
import com.photo.data.bookmark.model.BookmarkEntity
import com.photo.domain.common.entities.PhotoEntities

internal object BookmarkMapper {
    fun BookmarkEntity.toPhotoEntities(): PhotoEntities.Document = PhotoEntities.Document(
        collection = this.collection,
        thumbnailUrl = this.thumbnailUrl,
        imageUrl = this.imageUrl,
        displaySitename = this.displaySitename,
        datetime = this.datetime,
        isBookMark = this.isBookMark
    )

    fun PhotoEntities.Document.toBookMarkEntities(): BookmarkEntity = BookmarkEntity(
        collection = this.collection,
        thumbnailUrl = this.thumbnailUrl,
        imageUrl = this.imageUrl,
        displaySitename = this.displaySitename,
        datetime = this.datetime,
        isBookMark = this.isBookMark
    )
}