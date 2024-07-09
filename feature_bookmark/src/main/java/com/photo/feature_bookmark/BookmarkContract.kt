package com.photo.feature_bookmark

import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.base.UiEvent
import com.photo.presentation_core.base.UiSideEffect
import com.photo.presentation_core.base.UiState

class BookmarkContract {
    sealed class BookmarkEvent : UiEvent {
        object GetBookmarkList : BookmarkEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : BookmarkEvent()
        data class AddBookmark(val item : PhotoEntities.Document) : BookmarkEvent()
        data class SortTitle(val sortDefine: SortDefine) : BookmarkEvent()
        data class PriceFilter(val price : Int, val priceFilterDefine: PriceFilterDefine) : BookmarkEvent()
        data class AuthorFilter(val author : String) : BookmarkEvent()

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
        data class ShowToast(val message : String) : BookmarkSideEffect()
    }

}