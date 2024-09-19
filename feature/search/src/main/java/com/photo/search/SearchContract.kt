package com.photo.search

import androidx.paging.PagingData
import com.photo.domain.common.entities.PhotoEntities
import com.photo.base.UiEvent
import com.photo.base.UiSideEffect
import com.photo.base.UiState
import kotlinx.coroutines.flow.Flow

class SearchContract {
    sealed class SearchEvent : UiEvent {
        data class Search(val keyWord : String) : SearchEvent()
        data class RemoveBookmark(val item : PhotoEntities.Document) : SearchEvent()
        data class SaveBookmark(val item : PhotoEntities.Document) : SearchEvent()
        data class ShowErrorLayout(val message: String) : SearchEvent()
        data class ShowEmptyLayout(val message: String) : SearchEvent()
    }

    data class SearchUiState(
        val state : SearchState
    ) : UiState

    sealed class SearchState {
        object Init : SearchState()
        data class Empty(val message : String) : SearchState()
        data class Error(val message : String) : SearchState()

        data class Load(
            val item : Flow<PagingData<PhotoEntities.Document>>
        ) : SearchState()

    }

    sealed class SearchSideEffect : UiSideEffect {
        data class ShowToast(val id : Int) : SearchSideEffect()
    }
}