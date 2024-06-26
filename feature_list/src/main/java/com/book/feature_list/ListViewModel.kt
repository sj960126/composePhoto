package com.book.feature_list

import com.book.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(

) : BaseViewModel<ListContract.ListEvent,ListContract.ListUiState,ListContract.ListSideEffect>(){

    override fun createInitialState(): ListContract.ListUiState = ListContract.ListUiState(state = ListContract.ListState.Init)

    override fun handleEvent(event: ListContract.ListEvent) {
        TODO("Not yet implemented")
    }

}