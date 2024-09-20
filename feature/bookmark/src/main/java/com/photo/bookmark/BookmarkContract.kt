package com.photo.bookmark

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.photo.domain.common.entities.PhotoEntities
import com.photo.base.UiEvent
import com.photo.base.UiSideEffect
import com.photo.base.UiState
import kotlinx.collections.immutable.ImmutableList

class BookmarkContract {
    sealed class BookmarkEvent : UiEvent {
        object GetBookmarkList : BookmarkEvent()
        object ClearBookmark : BookmarkEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : BookmarkEvent()
        data class SaveBookmark(val item : PhotoEntities.Document) : BookmarkEvent()
        data class Search(val keyword : String) : BookmarkEvent()
        data class ClickItem(val item : String) : BookmarkEvent()
    }

    @Stable
    data class BookmarkUiState(
        val state : BookmarkState
    ) : UiState

    sealed class BookmarkState {
        object Loading : BookmarkState()
        object Empty : BookmarkState()

        @Immutable
        data class Success(
            val itemList : ImmutableList<PhotoEntities.Document>
        ) : BookmarkState()

    }

    sealed class BookmarkSideEffect : UiSideEffect {
        data class MoveDetailPage(val item : String) : BookmarkSideEffect()
        data class ShowToast(val id : Int) : BookmarkSideEffect()
    }

}