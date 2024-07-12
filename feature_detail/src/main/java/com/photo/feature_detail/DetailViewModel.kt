package com.photo.feature_detail

import androidx.lifecycle.viewModelScope
import com.photo.domain.bookmark.usecase.InsertBookmarkUseCase
import com.photo.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val insertBookmarkUseCase : InsertBookmarkUseCase
) : BaseViewModel<DetailContract.DetailEvent,DetailContract.DetailUiState,DetailContract.DetailSideEffect>(){

    override fun createInitialState(): DetailContract.DetailUiState = DetailContract.DetailUiState(state = DetailContract.DetailState.Loading)

    override fun handleEvent(event: DetailContract.DetailEvent) {
        when(event){
            is DetailContract.DetailEvent.Rending ->  rendingItem(item = event.item)
            is DetailContract.DetailEvent.AddBookmark -> addBookmark(item = event.item)
            is DetailContract.DetailEvent.RemoveBookmark -> removeBookmark(item = event.item)
        }
    }

    private fun rendingItem(item: PhotoEntities.Document){
        setState {
            copy(state = DetailContract.DetailState.Success(item))
        }
    }
    private fun removeBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            if(!item.thumbnailUrl.isNullOrEmpty())removeBookmarkUseCase.invoke(item.thumbnailUrl?:"")
            setEffect { DetailContract.DetailSideEffect.ShowToast("북마크 취소") }
        }
    }

    private fun addBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            insertBookmarkUseCase.invoke(item)
            setEffect { DetailContract.DetailSideEffect.ShowToast("북마크 저장") }
        }
    }

}