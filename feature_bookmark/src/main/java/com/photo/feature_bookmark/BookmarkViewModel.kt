package com.photo.feature_bookmark

import androidx.lifecycle.viewModelScope
import com.photo.domain.bookmark.usecase.*
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author songhyeonsu
 * Created 6/28/24 at 4:09 PM
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val fetchAllBookmarksUseCase : FetchAllBookmarksUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val insertBookmarkUseCase : InsertBookmarkUseCase,
    private val clearAllBookmarkUseCase: ClearAllBookmarkUseCase,
    private val fetchBookmarksByKeywordUseCase: FetchBookmarksByKeywordUseCase
) : BaseViewModel<BookmarkContract.BookmarkEvent,BookmarkContract.BookmarkUiState,BookmarkContract.BookmarkSideEffect>(){
    override fun createInitialState(): BookmarkContract.BookmarkUiState = BookmarkContract.BookmarkUiState(state = BookmarkContract.BookmarkState.Loading)

    init {
        fetchALlBookmarks()
    }

    override fun handleEvent(event: BookmarkContract.BookmarkEvent) {
        when(event){
            BookmarkContract.BookmarkEvent.GetBookmarkList -> fetchALlBookmarks()
            BookmarkContract.BookmarkEvent.ClearBookmark -> clearBookmark()
            is BookmarkContract.BookmarkEvent.RemoveBookmark -> removeBookmark(item = event.item)
            is BookmarkContract.BookmarkEvent.SaveBookmark -> saveBookmark(item = event.item)
            is BookmarkContract.BookmarkEvent.Search -> searchBookmark(event.keyword)
        }
    }

    private fun fetchALlBookmarks(){
        viewModelScope.launch {
            fetchAllBookmarksUseCase.invoke().let { bookmarkList ->
                setState {
                    copy(state = if(bookmarkList.isEmpty()) BookmarkContract.BookmarkState.Empty else BookmarkContract.BookmarkState.Success(itemList = bookmarkList))
                }
            }
        }
    }

    private fun removeBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            setState { copy(BookmarkContract.BookmarkState.Loading) }
            if(!item.thumbnailUrl.isNullOrEmpty())removeBookmarkUseCase.invoke(item.thumbnailUrl?:"")
            setEffect { BookmarkContract.BookmarkSideEffect.ShowToast(com.photo.presentation_core.R.string.bookmark_remove) }
            fetchALlBookmarks()
        }
    }

    private fun saveBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            setState { copy(BookmarkContract.BookmarkState.Loading) }
            insertBookmarkUseCase.invoke(item)
            setEffect { BookmarkContract.BookmarkSideEffect.ShowToast(com.photo.presentation_core.R.string.bookmark_save) }
            fetchALlBookmarks()
        }
    }
    private fun searchBookmark(keyword :String){
        viewModelScope.launch {
            setState { copy(BookmarkContract.BookmarkState.Loading) }
            fetchBookmarksByKeywordUseCase.invoke(keyword).let { bookmarkList ->
                setState {
                    copy(state = if(bookmarkList.isEmpty()) BookmarkContract.BookmarkState.Empty else BookmarkContract.BookmarkState.Success(itemList = bookmarkList))
                }
            }
        }
    }

    private fun clearBookmark(){
        if(currentState.state is BookmarkContract.BookmarkState.Empty) {
            setEffect { BookmarkContract.BookmarkSideEffect.ShowToast(com.photo.presentation_core.R.string.bookmark_remove_empty) }
        } else {
            viewModelScope.launch {
                clearAllBookmarkUseCase.invoke()
                setEffect { BookmarkContract.BookmarkSideEffect.ShowToast(com.photo.presentation_core.R.string.bookmark_all_delete) }
                setState { copy(BookmarkContract.BookmarkState.Empty) }
            }
        }
    }

}