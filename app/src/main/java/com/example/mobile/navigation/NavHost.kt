package com.example.mobile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile.R
import com.example.mobile.data.Character
import com.example.mobile.screen.CharacterComposed
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

// Map each route to its corresponding title resource
private val screenTitles = mapOf(
    MobileScreen.Home.name to R.string.title_home,
    MobileScreen.Settings.name to R.string.title_settings,
    MobileScreen.Compendium.name to R.string.title_compendium,
    MobileScreen.Creator.name to R.string.title_creator,
    MobileScreen.Weapons.name to R.string.title_weapons,
    MobileScreen.Spells.name to R.string.title_spells,
    MobileScreen.Items.name to R.string.title_items,
    MobileScreen.Classes.name to R.string.title_classes,
    MobileScreen.Races.name to R.string.title_races,
    MobileScreen.CompendiumCreator.name to R.string.title_compendium_creator,
    MobileScreen.Character.name to R.string.title_character
)

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MobileScreen.Home.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 10.dp)
    ) {
        composable(route = MobileScreen.Home.name) {
            Home(navController)
        }
        composable(route = MobileScreen.Settings.name) {
            Settings()
        }
        composable(route = MobileScreen.Compendium.name) {
            Compendium(navController)
        }
        composable(route = MobileScreen.Creator.name) {
            Creator(navController = navController)
        }
        composable(route = MobileScreen.Weapons.name) {
            Weapons()
        }
        composable(route = MobileScreen.Spells.name) {
            Spells()
        }
        composable(route = MobileScreen.Items.name) {
            Items()
        }
        composable(route = MobileScreen.Classes.name) {
            Classes()
        }
        composable(route = MobileScreen.Races.name) {
            Races()
        }
        composable(route = MobileScreen.CompendiumCreator.name) {
            CompendiumCreator(navController)
        }
        composable(route = "${MobileScreen.Character.name}/{characterName}") { backStackEntry ->
            val characterName = backStackEntry.arguments?.getString("characterName") ?: return@composable
            CharacterComposed(navController = navController, characterName = characterName)
        }
    }
}

// Function to retrieve the title for each route
@Composable
fun getScreenTitle(route: String?): String {
    val titleRes = screenTitles[route] ?: R.string.app_name
    return stringResource(id = titleRes)
}