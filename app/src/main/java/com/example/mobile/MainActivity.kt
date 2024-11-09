package com.example.mobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile.components.BottomBar
import com.example.mobile.components.TOPBAR_TYPES
import com.example.mobile.components.TopBar
import com.example.mobile.navigation.MobileScreen
import com.example.mobile.navigation.NavHostComposable
import com.example.mobile.ui.theme.MobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MobileTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 35.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            navBackStackEntry?.destination?.route?.let {
                                val topBarType = when {
                                    it == "Creator" -> TOPBAR_TYPES.CREATOR
                                    it in listOf("Weapons", "Spells", "Items", "Armor", "Classes", "Races", "CompendiumCreator") -> TOPBAR_TYPES.COMPENDIUM
                                    it.contains("Character/") -> TOPBAR_TYPES.CHARACTER
                                    else -> TOPBAR_TYPES.NORMAL
                                }

                                TopBar(
                                    onNavigateToSettings = {
                                        navController.navigate(MobileScreen.Settings.name) {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    onNavigateToCreator = {
                                        navController.navigate(MobileScreen.Creator.name) {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    onNavigateToHome = {
                                        navController.navigate(MobileScreen.Home.name) {
                                            popUpTo(MobileScreen.Home.name) { inclusive = true } // Clears stack to Home
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    onNavigateToCompendium = {
                                        navController.navigate(MobileScreen.Compendium.name) {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    title = it,
                                    type = topBarType
                                )
                            }
                        },
                        bottomBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            // List of routes where BottomBar should be hidden
                            val hideBottomBarRoutes = listOf(MobileScreen.Creator.name)

                            if (currentRoute !in hideBottomBarRoutes) {
                                BottomBar(
                                    selectedRoute = currentRoute ?: MobileScreen.Home.name,
                                    onNavigate = { screen ->
                                        navController.navigate(screen) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        },
                    ) { innerPadding ->
                        NavHostComposable(innerPadding, navController)
                    }
                }
            }
        }
    }
}
