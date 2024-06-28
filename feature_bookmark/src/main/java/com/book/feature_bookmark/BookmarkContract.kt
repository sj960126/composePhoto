package com.book.feature_bookmark

import androidx.paging.PagingData
import com.book.domain.common.entities.BookEntities
import com.book.presentation_core.base.UiEvent
import com.book.presentation_core.base.UiSideEffect
import com.book.presentation_core.base.UiState
import kotlinx.coroutines.flow.Flow

class BookmarkContract {
    sealed class BookmarkEvent : UiEvent {
        object GetBookmarkList : BookmarkEvent()
        data class RemoveBookmark(val item : BookEntities.Document) : BookmarkEvent()
        data class AddBookmark(val item : BookEntities.Document) : BookmarkEvent()
    }
    data class BookmarkUiState(
        val state : BookmarkState
    ) : UiState

    sealed class BookmarkState {
        object Loading : BookmarkState()
        object Empty : BookmarkState()

        object Error : BookmarkState()
        data class Success(
            val itemList : List<BookEntities.Document>
        ) : BookmarkState()

    }

    sealed class BookmarkSideEffect : UiSideEffect {
    }

}