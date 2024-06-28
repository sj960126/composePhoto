package com.book.feature_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.book.domain.bookmark.usecase.AddBookmarkUseCase
import com.book.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.book.domain.common.entities.BookEntities
import com.book.domain.search.usecase.GetBookListUseCase
import com.book.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val addBookmarkUseCase : AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    ) : BaseViewModel<ListContract.ListEvent, ListContract.ListUiState, ListContract.ListSideEffect>() {

    init {
        handleEvent(ListContract.ListEvent.LoadBooks)
    }

    override fun createInitialState(): ListContract.ListUiState = ListContract.ListUiState(state = ListContract.ListState.Loading)

    override fun handleEvent(event: ListContract.ListEvent) {
        when (event) {
            is ListContract.ListEvent.LoadBooks -> {
                viewModelScope.launch {
                    getSearchResults()
                        .cachedIn(viewModelScope)
                        .collectLatest { pagingData ->
                            setState {
                                copy(state = ListContract.ListState.Success(flowOf(pagingData)))
                            }
                        }
                }
            }
            is ListContract.ListEvent.AddBookmark -> addBookmark(item = event.item)
            is ListContract.ListEvent.RemoveBookmark -> removeBookmark(item = event.item)
        }
    }
    private fun removeBookmark(item: BookEntities.Document){
        viewModelScope.launch {
            removeBookmarkUseCase.invoke(item)
        }
    }

    private fun addBookmark(item: BookEntities.Document){
        viewModelScope.launch {
            addBookmarkUseCase.invoke(item)
        }
    }

    private fun getSearchResults(): Flow<PagingData<BookEntities.Document>> = getBookListUseCase()
}
