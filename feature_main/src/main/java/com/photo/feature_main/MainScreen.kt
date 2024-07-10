package com.photo.feature_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
import com.photo.domain.common.entities.PhotoEntities
import com.photo.feature_bookmark.BookmarkScreen
import com.photo.feature_detail.DetailScreen
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.google.gson.Gson
import com.photo.feature_list.SearchScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val selectedTabIndex = rememberSaveable { mutableStateOf(TabDefine.Search.ordinal) }
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var topBarTitle by rememberSaveable { mutableStateOf(MainNavigationConst.Search.topBarTitle) }
    val navigation = remember {
        object : MainNavigation {
            override fun navigateToDetail(item: String) {
                navController.navigate(
                    route = "${MainNavigationConst.Detail.route}/$item",
                ) {
                    launchSingleTop = true
                    popUpTo(MainNavigationConst.Search.route)
                }
            }
        }
    }


    LaunchedEffect(navBackStackEntry?.destination?.route) {
        bottomBarState.value = when (navBackStackEntry?.destination?.route) {
            MainNavigationConst.Search.route,
            MainNavigationConst.Bookmark.route -> true
            else -> false
        }
        topBarTitle = MainNavigationConst.getTopBarTitle(navBackStackEntry?.destination?.route?:"")
    }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = LocalColors.current.primary ) {
                Text(text = topBarTitle, color = LocalColors.current.tintWhite, style = LocalTypography.current.title1)
            }
        },
        bottomBar = {
            if (bottomBarState.value) {
                BottomBar(selectedTabIndex = selectedTabIndex.value, onTabSelected = { index ->
                    selectedTabIndex.value = index
                    when (index) {
                        TabDefine.Search.ordinal -> navController.navigate(MainNavigationConst.Search.route) {
                            popUpTo(MainNavigationConst.Search.route) { inclusive = true }
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
                text = { Text(text = tab.title, style = if(selectedTabIndex == index)LocalTypography.current.caption1 else LocalTypography.current.caption2 ) }
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
        startDestination = MainNavigationConst.Search.route,
    ) {
        composable(MainNavigationConst.Search.route) {
            SearchScreen(onItemClick = { navigation.navigateToDetail(it) })
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
                DetailScreen(bookDetail = Gson().fromJson(bookDetailJson, PhotoEntities.Document::class.java))
            }
        }
    }
}
