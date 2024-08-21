package com.example.mobile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile.home.Home


@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MobileScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 10.dp)
    ) {
        composable(route = MobileScreen.Home.name) {
            Home(
                onNavigateToSettings = { navController.navigate(MobileScreen.Settings.name) }
            )
        }
//        composable(route = MobileScreen.Profile.name) {
//            Profile()
//        }
//        composable(route = MobileScreen.Settings.name) {
//            Settings()
//        }
    }
}