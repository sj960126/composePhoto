package com.book.feature_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.book.domain.common.entities.BookEntities
import com.book.feature_bookmark.BookmarkScreen
import com.book.feature_detail.DetailScreen
import com.book.feature_list.ListScreen
import com.book.presentation_core.design_system.LocalColors
import com.book.presentation_core.design_system.LocalTypography
import com.google.gson.Gson

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val selectedTabIndex = rememberSaveable { mutableStateOf(TabDefine.List.ordinal) }
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val navigation = remember {
        object : MainNavigation {
            override fun navigateToDetail(item: String) {
                navController.navigate(
                    route = "${MainNavigationConst.Detail.route}/$item",
                ) {
                    launchSingleTop = true
                    popUpTo(MainNavigationConst.List.route)
                }
            }
        }
    }

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        bottomBarState.value = when (navBackStackEntry?.destination?.route) {
            MainNavigationConst.List.route,
            MainNavigationConst.Bookmark.route -> true
            else -> false
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomBarState.value) {
                BottomBar(selectedTabIndex = selectedTabIndex.value, onTabSelected = { index ->
                    selectedTabIndex.value = index
                    when (index) {
                        TabDefine.List.ordinal -> navController.navigate(MainNavigationConst.List.route) {
                            popUpTo(MainNavigationConst.List.route) { inclusive = true }
                        }
                        TabDefine.Bookmark.ordinal -> navController.navigate(MainNavigationConst.Bookmark.route) {
                            popUpTo(MainNavigationConst.Bookmark.route) { inclusive = true }
                        }
                    }
                })
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColors.current.white)
                .padding(innerPadding)
        ) {
            MainScreenNavigation(navController = navController, navigation = navigation)
        }
    }
}

@Composable
private fun BottomBar(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex, backgroundColor = LocalColors.current.primary) {
        TabDefine.values().forEachIndexed { index, tab ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(text = tab.title, style = LocalTypography.current.caption2) }
            )
        }
    }
}

@Composable
private fun MainScreenNavigation(
    navController: NavHostController,
    navigation: MainNavigation
) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationConst.List.route,
    ) {
        composable(MainNavigationConst.List.route) {
            ListScreen(onItemClick = { navigation.navigateToDetail(it) })
        }
        composable(MainNavigationConst.Bookmark.route) {
            BookmarkScreen(onItemClick = { navigation.navigateToDetail(it) })
        }
        composable(
            route = MainNavigationConst.Detail.route + "/{item}",
            arguments = listOf(navArgument("item") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookDetailJson = backStackEntry.arguments?.getString("item")
            if (bookDetailJson != null) {
                DetailScreen(bookDetail = Gson().fromJson(bookDetailJson, BookEntities.Document::class.java))
            }
        }
    }
}
