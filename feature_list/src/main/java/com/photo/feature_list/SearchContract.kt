package com.photo.feature_list

import androidx.paging.PagingData
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.base.UiEvent
import com.photo.presentation_core.base.UiSideEffect
import com.photo.presentation_core.base.UiState
import kotlinx.coroutines.flow.Flow

class SearchContract {
    sealed class SearchEvent : UiEvent {
        data class Search(val keyWord : String) : SearchEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : SearchEvent()
        data class AddBookmark(val item : PhotoEntities.Document) : SearchEvent()
    }

    data class SearchUiState(
        val state : SearchState
    ) : UiState

    sealed class SearchState {
        object Loading : SearchState()
        object Empty : SearchState()
        data class Success(
            val item : Flow<PagingData<PhotoEntities.Document>>
        ) : SearchState()

    }

    sealed class SearchSideEffect : UiSideEffect {
        data class ShowToast(val message : String) : SearchSideEffect()
    }
}