package com.book.feature_main

sealed class MainNavigationConst(
    val route: String
) {
    object Product : MainNavigationConst(
        "Search"
    )

    object Bookmark : MainNavigationConst(
        "Bookmark"
    )
}