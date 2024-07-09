package com.photo.data.search.mapper

import com.photo.data.search.model.PhotoResponse
import com.photo.domain.common.entities.PhotoEntities

object PhotoMapper {
    fun toDomain(bookResponse: PhotoResponse,bookMarkList : List<String>): PhotoEntities = PhotoEntities(documents = ArrayList(bookResponse.documents.map { mapDocumentToDomain(document = it, bookMarkList = bookMarkList) }))
    private fun mapDocumentToDomain(document: PhotoResponse.Document, bookMarkList : List<String>): PhotoEntities.Document =
        PhotoEntities.Document(
            collection = document.collection,
            thumbnailUrl = document.thumbnailUrl,
            imageUrl = document.imageUrl,
            displaySitename = document.displaySitename,
            datetime = document.datetime,
            isBookMark = bookMarkList.find { it == document.thumbnailUrl }?.isNotEmpty() == true
        )


}
