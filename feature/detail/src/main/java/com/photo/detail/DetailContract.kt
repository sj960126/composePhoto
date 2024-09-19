package com.photo.detail

import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.base.UiEvent
import com.photo.presentation_core.base.UiSideEffect
import com.photo.presentation_core.base.UiState

class DetailContract {
    sealed class DetailEvent : UiEvent {
        data class Rending(val item : PhotoEntities.Document) : DetailEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : DetailEvent()
        data class SaveBookmark(val item : PhotoEntities.Document) : DetailEvent()
    }

    data class DetailUiState(
        val state : DetailState
    ) : UiState

    sealed class DetailState {
        object Loading : DetailState()
        data class Success(val item : PhotoEntities.Document ) : DetailState()
    }

    sealed class DetailSideEffect : UiSideEffect {
        data class ShowToast(val id : Int) : DetailSideEffect()
    }

}