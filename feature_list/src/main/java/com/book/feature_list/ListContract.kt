package com.book.feature_list

import androidx.paging.PagingData
import com.book.domain.common.entities.BookEntities
import com.book.presentation_core.base.UiEvent
import com.book.presentation_core.base.UiSideEffect
import com.book.presentation_core.base.UiState
import kotlinx.coroutines.flow.Flow

class ListContract {
    sealed class ListEvent : UiEvent {
        object LoadBooks : ListEvent()
    }
    data class ListUiState(
        val state : ListState
    ) : UiState

    sealed class ListState {
        object Init : ListState()

        object Error : ListState()
        data class Success(
            val item : Flow<PagingData<BookEntities.Document>>
        ) : ListState()

    }

    sealed class ListSideEffect : UiSideEffect {

    }
}