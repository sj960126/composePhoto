package com.book.feature_main

import com.book.domain.search.entities.BookEntities

sealed class MainNavigationConst(
    val route: String
) {
    object List : MainNavigationConst(
        "List"
    )
    object Bookmark : MainNavigationConst(
        "Bookmark"
    )
    object Detail : MainNavigationConst(
        "Detail"
    )
}
interface MainNavigation {
    fun navigateToDetail(item : String)
}
enum class TabDefine(val title : String){
    List("전체 리스트"),Bookmark("즐겨찾기")
}