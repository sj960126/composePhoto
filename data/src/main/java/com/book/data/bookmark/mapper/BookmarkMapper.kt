package com.book.data.bookmark.mapper

import com.book.data.bookmark.model.BookmarkEntity
import com.book.domain.common.entities.BookEntities

object BookmarkMapper {
    fun BookmarkEntity.toDomain(): BookEntities.Document = BookEntities.Document(
        authors = this.authors?.split(",")?.map { it.trim() } as ArrayList<String>?,
        contents = this.contents,
        datetime = this.datetime,
        isbn = this.isbn,
        price = this.price,
        publisher = this.publisher,
        salePrice = this.salePrice,
        status = this.status,
        thumbnail = this.thumbnail,
        title = this.title,
        translators = this.translators?.split(",")?.map { it.trim() } as ArrayList<String>?,
        url = this.url,
        isBookMark = this.isBookmark
    )

    fun BookEntities.Document.toEntity(): BookmarkEntity = BookmarkEntity(
        authors = this.authors?.joinToString(","),
        contents = this.contents,
        datetime = this.datetime,
        isbn = this.isbn,
        price = this.price,
        publisher = this.publisher,
        salePrice = this.salePrice,
        status = this.status,
        thumbnail = this.thumbnail,
        title = this.title,
        translators = this.translators?.joinToString(","),
        url = this.url,
        isBookmark = this.isBookMark
    )
}