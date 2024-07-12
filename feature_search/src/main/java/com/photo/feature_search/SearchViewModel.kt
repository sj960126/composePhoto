package com.photo.feature_search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.photo.domain.bookmark.usecase.InsertBookmarkUseCase
import com.photo.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.photo.domain.common.entities.PhotoEntities
import com.photo.domain.search.usecase.FetchPaginatedPhotoUseCase
import com.photo.presentation_core.base.BaseViewModel
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

    override fun createInitialState(): SearchContract.SearchUiState = SearchContract.SearchUiState(state = SearchContract.SearchState.Init)

    override fun handleEvent(event: SearchContract.SearchEvent) {
        when (event) {
            is SearchContract.SearchEvent.Search -> {
                if(event.keyWord.isNotBlank()){
                    viewModelScope.launch {
                        fetchPaginatedPhotos(keyWord = event.keyWord)
                            .cachedIn(viewModelScope)
                            .collectLatest { pagingData ->
                                setState {
                                    copy(state = SearchContract.SearchState.Load(flowOf(pagingData)))
                                }
                            }
                    }
                }else{
                    setState { copy(state = SearchContract.SearchState.Init) }
                }
            }
            is SearchContract.SearchEvent.SaveBookmark -> saveBookmark(item = event.item)
            is SearchContract.SearchEvent.RemoveBookmark -> removeBookmark(item = event.item)
            is SearchContract.SearchEvent.ShowErrorLayout -> setState { copy(SearchContract.SearchState.Error(message = event.message)) }
            is SearchContract.SearchEvent.ShowEmptyLayout -> setState { copy(SearchContract.SearchState.Empty(message = event.message)) }
        }
    }
    private fun removeBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            if(!item.thumbnailUrl.isNullOrEmpty()) removeBookmarkUseCase.invoke(item.thumbnailUrl?:"")
            setEffect { SearchContract.SearchSideEffect.ShowToast(com.photo.presentation_core.R.string.bookmark_remove) }

        }
    }

    private fun saveBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            insertBookmarkUseCase.invoke(item)
            setEffect { SearchContract.SearchSideEffect.ShowToast(com.photo.presentation_core.R.string.bookmark_save) }
        }
    }

    private fun fetchPaginatedPhotos(keyWord : String): Flow<PagingData<PhotoEntities.Document>> = fetchPaginatedPhotoUseCase(keyWord = keyWord)
}
