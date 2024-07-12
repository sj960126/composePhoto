package com.photo.domain.search.usecase

import androidx.paging.PagingData
import com.photo.domain.common.entities.PhotoEntities
import com.photo.domain.search.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPaginatedPhotoUseCase @Inject constructor(
    private val repository: ISearchRepository
) {
    operator fun invoke(keyWord : String): Flow<PagingData<PhotoEntities.Document>> = repository.fetchPaginatedPhotos(keyword = keyWord)
}
