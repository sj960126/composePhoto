package com.photo.feature_bookmark

import androidx.lifecycle.viewModelScope
import com.photo.domain.bookmark.usecase.AddBookmarkUseCase
import com.photo.domain.bookmark.usecase.GetBookmarksUseCase
import com.photo.domain.bookmark.usecase.RemoveBookmarkUseCase
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
//            is BookmarkContract.BookmarkEvent.PriceFilter -> filterBookmarksByPrice(filterDefine = event.priceFilterDefine, price = event.price)
//            is BookmarkContract.BookmarkEvent.AuthorFilter -> filterBookmarksByAuthor(author = event.author)
            else->{}
        }
    }

//    private fun filterBookmarksByAuthor(author : String) {
//        viewModelScope.launch {
//            setState { copy(BookmarkContract.BookmarkState.Loading) }
//            getBookmarkUseCase.invoke().let { bookmarkList ->
//                setState {
//                    copy(state = if(bookmarkList.isEmpty()) BookmarkContract.BookmarkState.Empty else BookmarkContract.BookmarkState.Success(
//                        itemList = if(author.isNotEmpty()) bookmarkList.filter { it.authors?.contains(author) ?: false} else bookmarkList
//                    ))
//                }
//            }
//        }
//    }
//    private fun filterBookmarksByPrice(filterDefine: PriceFilterDefine, price : Int) {
//        viewModelScope.launch {
//            setState { copy(BookmarkContract.BookmarkState.Loading) }
//            getBookmarkUseCase.invoke().let { bookmarkList ->
//                setState {
//                    copy(state = if(bookmarkList.isEmpty()) BookmarkContract.BookmarkState.Empty else BookmarkContract.BookmarkState.Success(
//                        itemList = if(price == 0) bookmarkList else when(filterDefine){
//                            PriceFilterDefine.UP -> bookmarkList.filter { (it.salePrice ?: 0) >= price }
//                            PriceFilterDefine.DOWN -> bookmarkList.filter { (it.salePrice ?: 0) <= price }
//                        }
//                    ))
//                }
//            }
//        }
//    }
//    private fun sortBookmarksByTitle(sortDefine: SortDefine){
//        viewModelScope.launch {
//            setState { copy(BookmarkContract.BookmarkState.Loading) }
//            getBookmarkUseCase.invoke().let { bookmarkList ->
//                setState {
//                    copy(state = if(bookmarkList.isEmpty()) BookmarkContract.BookmarkState.Empty else BookmarkContract.BookmarkState.Success(
//                        itemList = when(sortDefine){
//                            SortDefine.ASCENDING -> bookmarkList.sortedBy { it.thumbnailUrl }
//                            SortDefine.DESCENDING -> bookmarkList.sortedByDescending { it.thumbnailUrl }
//                        }
//                    ))
//                }
//            }
//        }
//    }

    private fun getBookmarkList(){
        viewModelScope.launch {
            getBookmarkUseCase.invoke().let { bookmarkList ->
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
            setEffect { BookmarkContract.BookmarkSideEffect.ShowToast("북마크 취소") }
            getBookmarkList()
        }
    }

    private fun addBookmark(item: PhotoEntities.Document){
        viewModelScope.launch {
            setState { copy(BookmarkContract.BookmarkState.Loading) }
            addBookmarkUseCase.invoke(item)
            setEffect { BookmarkContract.BookmarkSideEffect.ShowToast("북마크 저장") }
            getBookmarkList()
        }
    }

}