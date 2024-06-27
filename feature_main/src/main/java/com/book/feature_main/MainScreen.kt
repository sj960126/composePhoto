package com.book.feature_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.book.feature_list.ListScreen
import com.book.presentation_core.design_system.LocalColors
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            var selectedTabIndex by remember { mutableStateOf(TabDefine.List.ordinal) }
            TabRow(selectedTabIndex = selectedTabIndex) {
                TabDefine.values().forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            when (index) {
                                TabDefine.List.ordinal -> navController.navigate(MainNavigationConst.List.route)
                                TabDefine.Bookmark.ordinal -> navController.navigate(MainNavigationConst.Bookmark.route)
                            }
                        },
                        text = { Text(tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColors.current.white)
        ) {
            MainScreenNavigation(navController = navController, paddingValues = innerPadding)
        }
    }
}

@Composable
private fun MainScreenNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = MainNavigationConst.List.route) {
        composable(MainNavigationConst.List.route) {
            ListScreen(navController = navController)
        }
        composable(MainNavigationConst.Bookmark.route) {
            BookmarkScreen()
        }
    }
}



@Composable
fun BookmarkScreen() {
    Text("Bookmark Screen")
}
