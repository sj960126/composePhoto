package com.photo.bookmark

import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.base.UiEvent
import com.photo.presentation_core.base.UiSideEffect
import com.photo.presentation_core.base.UiState

class BookmarkContract {
    sealed class BookmarkEvent : UiEvent {
        object GetBookmarkList : BookmarkEvent()
        object ClearBookmark : BookmarkEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : BookmarkEvent()
        data class SaveBookmark(val item : PhotoEntities.Document) : BookmarkEvent()
        data class Search(val keyword : String) : BookmarkEvent()

    }

    data class BookmarkUiState(
        val state : BookmarkState
    ) : UiState

    sealed class BookmarkState {
        object Loading : BookmarkState()
        object Empty : BookmarkState()
        data class Success(
            val itemList : List<PhotoEntities.Document>
        ) : BookmarkState()

    }

    sealed class BookmarkSideEffect : UiSideEffect {
        data class ShowToast(val id : Int) : BookmarkSideEffect()
    }

}