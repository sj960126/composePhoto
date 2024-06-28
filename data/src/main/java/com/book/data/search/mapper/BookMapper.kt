package com.book.data.search.mapper

import com.book.data.search.model.BookResponse
import com.book.domain.common.entities.BookEntities

object BookMapper {

    fun mapToDomain(bookResponse: BookResponse,bookMarkList : List<String>): BookEntities = BookEntities(documents = ArrayList(bookResponse.documents.map { mapDocumentToDomain(document = it, bookMarkList = bookMarkList) }))

    private fun mapDocumentToDomain(document: BookResponse.Document, bookMarkList : List<String>): BookEntities.Document =
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
            url = document.url,
            isBookMark = bookMarkList.find { it == document.title }?.isNotEmpty() == true
        )


}
