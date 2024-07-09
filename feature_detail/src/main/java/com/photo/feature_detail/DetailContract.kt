package com.photo.feature_detail

import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.base.UiEvent
import com.photo.presentation_core.base.UiSideEffect
import com.photo.presentation_core.base.UiState

class DetailContract {
    sealed class DetailEvent : UiEvent {
        data class Rending(val item : BookEntities.Document) : DetailEvent()
        data class RemoveBookmark(val item : BookEntities.Document) : DetailEvent()
        data class AddBookmark(val item : BookEntities.Document) : DetailEvent()
    }

    data class DetailUiState(
        val state : DetailState
    ) : UiState

    sealed class DetailState {
        object Loading : DetailState()
        data class Success(val item : BookEntities.Document ) : DetailState()
    }

    sealed class DetailSideEffect : UiSideEffect {
        data class ShowToast(val message : String) : DetailSideEffect()
    }

}