package com.book.feature_main

sealed class MainNavigationConst(
    val route: String
) {
    object List : MainNavigationConst(
        "List"
    )

    object Bookmark : MainNavigationConst(
        "Bookmark"
    )
}

enum class TabDefine(val title : String){
    List("전체 리스트"),Bookmark("즐겨찾기")
}