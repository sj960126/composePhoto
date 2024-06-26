package com.book.feature_main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
    Scaffold { innerPadding ->
        MainScreenNavigation(navController = navController, paddingValues = innerPadding)
    }
}

@Composable
private fun MainScreenNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = MainNavigationConst.Product.route) {
        composable(MainNavigationConst.Product.route) {
        }

        composable(MainNavigationConst.Bookmark.route) {
        }
    }
}
