package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import javax.inject.Inject

class FetchBookmarksByKeywordUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend operator fun invoke(keyWord : String) = repository.fetchBookmarksByKeyword(keyword = keyWord)

}