package com.photo.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.layout.FoldingFeature
import com.google.gson.Gson
import com.photo.domain.common.entities.PhotoEntities
import com.photo.bookmark.BookmarkScreen
import com.photo.detail.DetailScreen
import com.photo.search.SearchScreen
import com.photo.design_system.LocalColors
import com.photo.design_system.LocalTypography
import com.photo.language.LanguageDefine
import com.photo.state.rememberFoldableState

@Composable
fun MainScreen(onLanguageChange: (String) -> Unit) {
    val navController = rememberNavController()
    val selectedTabIndex = rememberSaveable { mutableStateOf(TabDefine.Search.ordinal) }
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var topBarTitleRes by rememberSaveable { mutableStateOf(MainNavigationConst.Search.topBarTitleRes) }
    val foldableState by rememberFoldableState(LocalContext.current)
    val isDualPane = foldableState?.state == FoldingFeature.State.HALF_OPENED && foldableState?.isSeparating == true

    val navigation = remember {
        object : MainNavigation {
            override fun navigateToDetail(item: String) {
                navController.navigate(
                    route = "${MainNavigationConst.Detail.route}/$item",
                )
            }
        }
    }

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        if(navBackStackEntry?.destination?.route == null) return@LaunchedEffect
        bottomBarState.value = when (navBackStackEntry?.destination?.route) {
            MainNavigationConst.Search.route,
            MainNavigationConst.Bookmark.route -> true
            else -> false
        }
        topBarTitleRes = MainNavigationConst.getTopBarTitleRes(
            navBackStackEntry?.destination?.route ?: ""
        ) ?:0
    }

    Scaffold(
        topBar = {
            TopBar(titleRes = topBarTitleRes, onLanguageChange = onLanguageChange)
        },
        bottomBar = {
            if (bottomBarState.value) {
                BottomBar(selectedTabIndex = selectedTabIndex.value, onTabSelected = { index ->
                    selectedTabIndex.value = index
                    when (index) {
                        TabDefine.Search.ordinal -> navController.navigate(MainNavigationConst.Search.route) {
                            popUpTo(MainNavigationConst.Search.route)
                            launchSingleTop = true
                        }
                        TabDefine.Bookmark.ordinal -> navController.navigate(MainNavigationConst.Bookmark.route) {
                            popUpTo(MainNavigationConst.Bookmark.route)
                            launchSingleTop = true
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
            MainScreenNavigation(navController = navController, navigation = navigation, isDualPane = isDualPane)
        }
    }
}

@Composable
private fun MainScreenNavigation(
    navController: NavHostController,
    navigation: MainNavigation,
    isDualPane : Boolean
) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationConst.Search.route,
    ) {
        composable(MainNavigationConst.Search.route) {
            SearchScreen(
                isDualPane = isDualPane,
                onNavigateToDetail = { navigation.navigateToDetail(it) })
        }
        composable(MainNavigationConst.Bookmark.route) {
            BookmarkScreen(
                isDualPane = isDualPane,
                onNavigateToDetail = { navigation.navigateToDetail(it) })
        }
        composable(
            route = MainNavigationConst.Detail.route + "/{item}",
            arguments = listOf(navArgument("item") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookDetailJson = backStackEntry.arguments?.getString("item")
            if (bookDetailJson != null) {
                DetailScreen(
                    bookDetail = Gson().fromJson(
                        bookDetailJson,
                        PhotoEntities.Document::class.java
                    )
                )
            }
        }
    }
}

@Composable
fun TopBar(titleRes: Int, onLanguageChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(backgroundColor = LocalColors.current.primary) {
        Text(
            text = stringResource(id = titleRes),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = LocalColors.current.tintWhite,
            style = LocalTypography.current.title1
        )

        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = LocalColors.current.tintWhite)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                LanguageDefine.values().forEach {
                    DropdownMenuItem(onClick = {
                        onLanguageChange(it.code)
                        expanded = false
                    }) {
                        Text(it.title)
                    }
                }
            }
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
                text = { Text(text = stringResource(id = tab.titleRes), style = if(selectedTabIndex == index) LocalTypography.current.caption1 else LocalTypography.current.caption2 ) }
            )
        }
    }
}