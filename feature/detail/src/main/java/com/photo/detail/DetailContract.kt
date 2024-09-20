package com.photo.detail

import androidx.compose.runtime.Stable
import com.photo.domain.common.entities.PhotoEntities
import com.photo.base.UiEvent
import com.photo.base.UiSideEffect
import com.photo.base.UiState

class DetailContract {
    sealed class DetailEvent : UiEvent {
        data class Rending(val item : PhotoEntities.Document) : DetailEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : DetailEvent()
        data class SaveBookmark(val item : PhotoEntities.Document) : DetailEvent()
    }

    @Stable
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