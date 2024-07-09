package com.photo.feature_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.photo.domain.bookmark.usecase.AddBookmarkUseCase
import com.photo.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.photo.domain.common.entities.PhotoEntities
import com.photo.domain.search.usecase.GetSearchPhotoList
import com.photo.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getBookListUseCase: GetSearchPhotoList,
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
                            pagingData.map {  }
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
    private fun removeBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            if(!item.thumbnailUrl.isNullOrEmpty()) removeBookmarkUseCase.invoke(item.thumbnailUrl?:"")
            setEffect { ListContract.ListSideEffect.ShowToast("북마크 취소") }
        }
    }

    private fun addBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            addBookmarkUseCase.invoke(item)
            setEffect { ListContract.ListSideEffect.ShowToast("북마크 저장") }
        }
    }

    private fun getSearchResults(): Flow<PagingData<PhotoEntities.Document>> = getBookListUseCase(keyWord = "고양이")
}
