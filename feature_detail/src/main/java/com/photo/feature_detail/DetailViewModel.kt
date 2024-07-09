package com.photo.feature_detail

import androidx.lifecycle.viewModelScope
import com.photo.domain.bookmark.usecase.AddBookmarkUseCase
import com.photo.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val addBookmarkUseCase : AddBookmarkUseCase
) : BaseViewModel<DetailContract.DetailEvent,DetailContract.DetailUiState,DetailContract.DetailSideEffect>(){

    override fun createInitialState(): DetailContract.DetailUiState = DetailContract.DetailUiState(state = DetailContract.DetailState.Loading)

    override fun handleEvent(event: DetailContract.DetailEvent) {
        when(event){
            is DetailContract.DetailEvent.Rending ->  rendingItem(item = event.item)
            is DetailContract.DetailEvent.AddBookmark -> addBookmark(item = event.item)
            is DetailContract.DetailEvent.RemoveBookmark -> removeBookmark(item = event.item)
        }
    }

    private fun rendingItem(item: BookEntities.Document){
        setState {
            copy(state = DetailContract.DetailState.Success(item))
        }
    }
    private fun removeBookmark(item: BookEntities.Document){
        viewModelScope.launch {
            if(!item.title.isNullOrEmpty())removeBookmarkUseCase.invoke(item.title?:"")
            setEffect { DetailContract.DetailSideEffect.ShowToast("북마크 취소") }
        }
    }

    private fun addBookmark(item: BookEntities.Document){
        viewModelScope.launch {
            addBookmarkUseCase.invoke(item)
            setEffect { DetailContract.DetailSideEffect.ShowToast("북마크 저장") }
        }
    }

}