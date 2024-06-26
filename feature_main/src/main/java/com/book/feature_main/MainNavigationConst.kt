package com.book.feature_main

sealed class MainNavigationConst(
    val route: String
) {
    object Search : MainNavigationConst(
        "Search"
    )

    object Bookmark : MainNavigationConst(
        "Bookmark"
    )
}

enum class TabDefine(val title : String){
    Search("검색"),Bookmark("즐겨찾기")
}