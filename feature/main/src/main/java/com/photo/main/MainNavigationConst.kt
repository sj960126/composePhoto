package com.photo.main

import com.photo.ui.R

sealed class MainNavigationConst(
    val route: String,
    val topBarTitleRes : Int
) {
    object Search : MainNavigationConst(
        route = "Search",
        topBarTitleRes = R.string.search_label
    )
    object Bookmark : MainNavigationConst(
        route = "Bookmark",
        topBarTitleRes = R.string.bookmark_label
    )
    object Detail : MainNavigationConst(
        route = "Detail",
        topBarTitleRes = R.string.detail_label
    )

    companion object {
        fun getTopBarTitleRes(route: String): Int? = when {
            route.startsWith(Search.route) -> Search.topBarTitleRes
            route.startsWith(Bookmark.route) -> Bookmark.topBarTitleRes
            route.startsWith(Detail.route) -> Detail.topBarTitleRes
            else -> null
        }
    }
}

interface MainNavigation {
    fun navigateToDetail(item : String)
}
enum class TabDefine(val titleRes : Int){
    Search(R.string.search_label),Bookmark(R.string.bookmark_label)
}