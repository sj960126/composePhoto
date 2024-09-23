package com.photo.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.photo.domain.bookmark.usecase.InsertBookmarkUseCase
import com.photo.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.photo.domain.common.entities.PhotoEntities
import com.photo.domain.search.usecase.FetchPaginatedPhotoUseCase
import com.photo.ui.R
import com.photo.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchPaginatedPhotoUseCase: FetchPaginatedPhotoUseCase,
    private val insertBookmarkUseCase : InsertBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
) : BaseViewModel<SearchContract.SearchEvent, SearchContract.SearchUiState, SearchContract.SearchSideEffect>() {

    override fun createInitialState(): SearchContract.SearchUiState =
        SearchContract.SearchUiState(state = SearchContract.SearchState.Init)

    override fun handleEvent(event: SearchContract.SearchEvent) {
        when (event) {
            is SearchContract.SearchEvent.Search -> if(event.keyWord.isNotBlank()) searchPhoto(event.keyWord) else resetSearchState()
            is SearchContract.SearchEvent.ClickItem -> setEffect { SearchContract.SearchSideEffect.MoveDetailPage(item = event.item) }
            is SearchContract.SearchEvent.SaveBookmark -> saveBookmark(item = event.item)
            is SearchContract.SearchEvent.RemoveBookmark -> removeBookmark(item = event.item)
            is SearchContract.SearchEvent.ShowErrorLayout -> updateSearchStateWithError(event.message)
            is SearchContract.SearchEvent.ShowEmptyLayout -> updateSearchStateWithEmpty(event.message)
        }
    }

    private fun removeBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            if(!item.thumbnailUrl.isNullOrEmpty()) removeBookmarkUseCase(item.thumbnailUrl?:"")
            setEffect { SearchContract.SearchSideEffect.ShowToast(R.string.bookmark_remove) }

        }
    }

    private fun updateSearchStateWithError(errorMessage: String?) {
        setState {
            copy(state = SearchContract.SearchState.Error(errorMessage ?: "An unknown error occurred"))
        }
    }

    private fun updateSearchStateWithEmpty(emptyMessage: String?) {
        setState {
            copy(state = SearchContract.SearchState.Empty(emptyMessage ?: "Empty Layout"))
        }
    }

    private fun updateSearchStateWithPagingData(pagingData: PagingData<PhotoEntities.Document>) {
        setState {
            copy(state = SearchContract.SearchState.Load(flowOf(pagingData)))
        }
    }

    private fun resetSearchState() {
        setState {
            copy(state = SearchContract.SearchState.Init)
        }
    }

    private fun saveBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            insertBookmarkUseCase(item)
            setEffect { SearchContract.SearchSideEffect.ShowToast(R.string.bookmark_save) }
        }
    }

    private fun searchPhoto(keyWord: String) {
        viewModelScope.launch {
            fetchPaginatedPhotos(keyWord = keyWord)
                .cachedIn(viewModelScope)
                .catch { error ->
                    updateSearchStateWithError(errorMessage = error.message)
                }
                .collectLatest { pagingData ->
                    updateSearchStateWithPagingData(pagingData = pagingData)
                }
        }
    }

    private fun fetchPaginatedPhotos(keyWord : String): Flow<PagingData<PhotoEntities.Document>> = fetchPaginatedPhotoUseCase(keyWord = keyWord)

}
