package com.book.data.search.mapper

import com.book.data.search.model.BookResponse
import com.book.domain.common.entities.BookEntities

object BookMapper {

    fun mapToDomain(bookResponse: BookResponse): BookEntities = BookEntities(documents = ArrayList(bookResponse.documents.map { mapDocumentToDomain(it) }))

    private fun mapDocumentToDomain(document: BookResponse.Document): BookEntities.Document =
        BookEntities.Document(
            authors = document.authors,
            contents = document.contents,
            datetime = document.datetime,
            isbn = document.isbn,
            price = document.price,
            publisher = document.publisher,
            salePrice = document.salePrice,
            status = document.status,
            thumbnail = document.thumbnail,
            title = document.title,
            translators = document.translators,
            url = document.url
        )

}
