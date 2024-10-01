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
import com.example.mobile.screen.classes.Classes
import com.example.mobile.screen.compendium.Compendium
import com.example.mobile.screen.compendium.creator.CompendiumCreator
import com.example.mobile.screen.creator.Creator
import com.example.mobile.screen.home.Home
import com.example.mobile.screen.items.Items
import com.example.mobile.screen.races.Races
import com.example.mobile.screen.settings.Settings
import com.example.mobile.screen.spells.Spells
import com.example.mobile.screen.weapons.Weapons
import dagger.hilt.android.qualifiers.ApplicationContext


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
        composable(route = MobileScreen.Weapons.name){
            Weapons()
        }
        composable(route = MobileScreen.Spells.name){
            Spells()
        }
        composable(route = MobileScreen.Items.name){
            Items()
        }
        composable(route = MobileScreen.Classes.name){
            Classes()
        }
        composable(route = MobileScreen.Races.name){
            Races()
        }
        composable(route = MobileScreen.CompendiumCreator.name){
            CompendiumCreator(navController)
        }
    }
}