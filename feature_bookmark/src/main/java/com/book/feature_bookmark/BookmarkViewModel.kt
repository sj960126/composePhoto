package com.book.feature_bookmark

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.book.domain.bookmark.usecase.AddBookmarkUseCase
import com.book.domain.bookmark.usecase.GetBookmarksUseCase
import com.book.domain.bookmark.usecase.RemoveBookmarkUseCase
import com.book.domain.common.entities.BookEntities
import com.book.presentation_core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author songhyeonsu
 * Created 6/28/24 at 4:09 PM
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkUseCase: GetBookmarksUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val addBookmarkUseCase : AddBookmarkUseCase
) : BaseViewModel<BookmarkContract.BookmarkEvent,BookmarkContract.BookmarkUiState,BookmarkContract.BookmarkSideEffect>(){
    override fun createInitialState(): BookmarkContract.BookmarkUiState = BookmarkContract.BookmarkUiState(state = BookmarkContract.BookmarkState.Loading)

    init {
        getBookmarkList()
    }

    override fun handleEvent(event: BookmarkContract.BookmarkEvent) {
        when(event){
            BookmarkContract.BookmarkEvent.GetBookmarkList -> getBookmarkList()
            is BookmarkContract.BookmarkEvent.RemoveBookmark -> removeBookmark(item = event.item)
            is BookmarkContract.BookmarkEvent.AddBookmark -> addBookmark(item = event.item)

        }
    }

    private fun getBookmarkList(){
        viewModelScope.launch {
            getBookmarkUseCase.invoke().let {bookmarkList ->
                setState {
                    Log.d("디버그","${bookmarkList}")
                    copy(state = if(bookmarkList.isEmpty()) BookmarkContract.BookmarkState.Empty else BookmarkContract.BookmarkState.Success(itemList = bookmarkList))
                }
            }
        }
    }

    private fun removeBookmark(item: BookEntities.Document){
        viewModelScope.launch {
            setState { copy(BookmarkContract.BookmarkState.Loading) }
            removeBookmarkUseCase.invoke(item)
            getBookmarkList()
        }
    }

    private fun addBookmark(item: BookEntities.Document){
        viewModelScope.launch {
            setState { copy(BookmarkContract.BookmarkState.Loading) }
            addBookmarkUseCase.invoke(item)
            getBookmarkList()
        }
    }

}