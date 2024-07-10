package com.photo.feature_main

sealed class MainNavigationConst(
    val route: String,
    val topBarTitle : String
) {
    object Search : MainNavigationConst(
        route = "Search",
        topBarTitle = "검색"
    )
    object Bookmark : MainNavigationConst(
        route = "Bookmark",
        topBarTitle = "북마크"
    )
    object Detail : MainNavigationConst(
        route = "Detail",
        topBarTitle = "상세페이지"
    )

    companion object {
        fun getTopBarTitle(route: String): String = when (route) {
            Search.route -> Search.topBarTitle
            Bookmark.route -> Bookmark.topBarTitle
            Detail.route -> Detail.topBarTitle
            else -> ""
        }

    }
}

interface MainNavigation {
    fun navigateToDetail(item : String)
}
enum class TabDefine(val title : String){
    Search("검색"),Bookmark("즐겨찾기")
}