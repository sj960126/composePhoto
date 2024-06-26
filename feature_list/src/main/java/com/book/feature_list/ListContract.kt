package com.book.feature_list

import com.book.presentation_core.base.UiEvent
import com.book.presentation_core.base.UiSideEffect
import com.book.presentation_core.base.UiState

class ListContract {
    sealed class ListEvent : UiEvent {

    }

    data class ListUiState(
        val state : ListState
    ) : UiState

    sealed class ListState {
        object Init : ListState()
    }

    sealed class ListSideEffect : UiSideEffect {

    }
}