package com.book.feature_main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            var selectedTabIndex by remember { mutableStateOf(TabDefine.Search.ordinal) }
            TabRow(selectedTabIndex = selectedTabIndex) {
                TabDefine.values().forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            when (index) {
                                0 -> navController.navigate(MainNavigationConst.Search.route)
                                1 -> navController.navigate(MainNavigationConst.Bookmark.route)
                            }
                        },
                        text = { Text(tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        MainScreenNavigation(navController = navController, paddingValues = innerPadding)
    }
}

@Composable
private fun MainScreenNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = MainNavigationConst.Search.route) {
        composable(MainNavigationConst.Search.route) {
            SearchScreen()
        }
        composable(MainNavigationConst.Bookmark.route) {
            BookmarkScreen()
        }
    }
}


@Composable
fun SearchScreen() {
    Text("Search Screen")
}

@Composable
fun BookmarkScreen() {
    Text("Bookmark Screen")
}
