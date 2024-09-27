package com.photo.search

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.photo.domain.common.entities.PhotoEntities
import com.photo.base.UiEvent
import com.photo.base.UiSideEffect
import com.photo.base.UiState
import kotlinx.coroutines.flow.Flow

class SearchContract {
    sealed interface SearchEvent : UiEvent {
        data class Search(val keyWord : String) : SearchEvent
        data class RemoveBookmark(val item : PhotoEntities.Document) : SearchEvent
        data class SaveBookmark(val item : PhotoEntities.Document) : SearchEvent
        data class ShowErrorLayout(val message: String) : SearchEvent
        data class ShowEmptyLayout(val message: String) : SearchEvent
        data class ClickItem(val item : String) : SearchEvent
    }

    @Stable
    data class SearchState(
        val searchKeyWord :String? = null,
        val state : SearchUiState
    ) : UiState

    sealed interface SearchUiState {
        object Init : SearchUiState
        data class Empty(val message : String) : SearchUiState
        data class Error(val message : String) : SearchUiState

        @Stable
        data class Load(
            val item : Flow<PagingData<PhotoEntities.Document>>
        ) : SearchUiState

    }

    sealed interface SearchSideEffect : UiSideEffect {
        data class ShowToast(val id : Int) : SearchSideEffect
        data class MoveDetailPage(val item : String) : SearchSideEffect
    }
}