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
class SearchViewModel @Inject constructor(
    private val getBookListUseCase: GetSearchPhotoList,
    private val addBookmarkUseCase : AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
) : BaseViewModel<SearchContract.SearchEvent, SearchContract.SearchUiState, SearchContract.SearchSideEffect>() {

    override fun createInitialState(): SearchContract.SearchUiState = SearchContract.SearchUiState(state = SearchContract.SearchState.Loading)

    override fun handleEvent(event: SearchContract.SearchEvent) {
        when (event) {
            is SearchContract.SearchEvent.Search -> {
                viewModelScope.launch {
                    getSearchResults(keyWord = event.keyWord)
                        .cachedIn(viewModelScope)
                        .collectLatest { pagingData ->
                            pagingData.map {  }
                            setState {
                                copy(state = SearchContract.SearchState.Success(flowOf(pagingData)))
                            }
                        }
                }
            }
            is SearchContract.SearchEvent.AddBookmark -> addBookmark(item = event.item)
            is SearchContract.SearchEvent.RemoveBookmark -> removeBookmark(item = event.item)
        }
    }
    private fun removeBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            if(!item.thumbnailUrl.isNullOrEmpty()) removeBookmarkUseCase.invoke(item.thumbnailUrl?:"")
            setEffect { SearchContract.SearchSideEffect.ShowToast("북마크 취소") }
        }
    }

    private fun addBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            addBookmarkUseCase.invoke(item)
            setEffect { SearchContract.SearchSideEffect.ShowToast("북마크 저장") }
        }
    }

    private fun getSearchResults(keyWord : String): Flow<PagingData<PhotoEntities.Document>> = getBookListUseCase(keyWord = keyWord)
}
