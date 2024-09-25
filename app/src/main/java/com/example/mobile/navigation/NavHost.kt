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
import com.example.mobile.screen.compendium.Compendium
import com.example.mobile.screen.creator.Creator
import com.example.mobile.screen.home.Home
import com.example.mobile.screen.items.Items
import com.example.mobile.screen.settings.Settings


@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MobileScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 10.dp)
    ) {
        composable(route = MobileScreen.Home.name) {
            Home()
        }
        composable(route = MobileScreen.Settings.name) {
            Settings()
        }
        composable(route = MobileScreen.Compendium.name) {
            Compendium(navController)
        }
        composable(route = MobileScreen.Creator.name){
            Creator()
        }
        composable(route = MobileScreen.Items.name){
            Items()
        }
    }
}