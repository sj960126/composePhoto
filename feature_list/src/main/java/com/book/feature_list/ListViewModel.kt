package com.book.feature_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.book.domain.search.entities.BookEntities
import com.book.domain.search.usecase.GetBookListUseCase
import com.book.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCase : GetBookListUseCase
) : BaseViewModel<ListContract.ListEvent,ListContract.ListUiState,ListContract.ListSideEffect>(){

    override fun createInitialState(): ListContract.ListUiState = ListContract.ListUiState(state = ListContract.ListState.Init)

    override fun handleEvent(event: ListContract.ListEvent) {
        TODO("Not yet implemented")
    }


    fun getSearchResults(): Flow<PagingData<BookEntities.Document>>  = useCase().cachedIn(viewModelScope)

}