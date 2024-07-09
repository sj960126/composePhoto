package com.photo.feature_list

import androidx.paging.PagingData
import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.base.UiEvent
import com.photo.presentation_core.base.UiSideEffect
import com.photo.presentation_core.base.UiState
import kotlinx.coroutines.flow.Flow

class ListContract {
    sealed class ListEvent : UiEvent {
        object LoadBooks : ListEvent()
        data class RemoveBookmark(val item : BookEntities.Document) : ListEvent()
        data class AddBookmark(val item : BookEntities.Document) : ListEvent()
    }
    data class ListUiState(
        val state : ListState
    ) : UiState

    sealed class ListState {
        object Loading : ListState()
        object Empty : ListState()
        data class Success(
            val item : Flow<PagingData<BookEntities.Document>>
        ) : ListState()

    }

    sealed class ListSideEffect : UiSideEffect {
        data class ShowToast(val message : String) : ListSideEffect()
    }
}