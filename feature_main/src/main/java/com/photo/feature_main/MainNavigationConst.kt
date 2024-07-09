package com.photo.feature_main

sealed class MainNavigationConst(
    val route: String
) {
    object Search : MainNavigationConst(
        "Search"
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
    Search("검색"),Bookmark("즐겨찾기")
}