package com.photo.main

sealed class MainNavigationConst(
    val route: String,
    val topBarTitleRes : Int
) {
    object Search : MainNavigationConst(
        route = "Search",
        topBarTitleRes = com.photo.presentation_core.R.string.search_label
    )
    object Bookmark : MainNavigationConst(
        route = "Bookmark",
        topBarTitleRes = com.photo.presentation_core.R.string.bookmark_label
    )
    object Detail : MainNavigationConst(
        route = "Detail",
        topBarTitleRes = com.photo.presentation_core.R.string.detail_label
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
    Search(com.photo.presentation_core.R.string.search_label),Bookmark(com.photo.presentation_core.R.string.bookmark_label)
}