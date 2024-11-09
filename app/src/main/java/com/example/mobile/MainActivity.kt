package com.example.mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile.components.BottomBar
import com.example.mobile.components.TOPBAR_TYPES
import com.example.mobile.components.TopBar
import com.example.mobile.navigation.MobileScreen
import com.example.mobile.navigation.NavHostComposable
import com.example.mobile.security.BiometricAuthManager
import com.example.mobile.ui.theme.MobileTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var biometricAuthManager: BiometricAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isAuthenticated by remember { mutableStateOf(false) }

            if (isAuthenticated) {
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
                                                popUpTo(MobileScreen.Home.name) { inclusive = true }
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
            } else {
                BiometricAuthentication(
                    isAuthenticated = isAuthenticated,
                    onSuccess = { isAuthenticated = true },
                    biometricAuthManager = biometricAuthManager
                )
            }
        }
    }

    @Composable
    fun BiometricAuthentication(
        isAuthenticated: Boolean,
        onSuccess: () -> Unit,
        biometricAuthManager: BiometricAuthManager
    ) {
        val context = LocalContext.current
        val biometricManager = remember { BiometricManager.from(context) }
        val isBiometricAvailable = remember {
            biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        }

        when (isBiometricAvailable) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                if (!isAuthenticated) {
                    biometricAuthManager.authenticate(context, {}, onSuccess, {})
                }
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Text(text = "This device does not support biometric authentication.")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Text(text = "Biometric authentication is currently unavailable.")
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                Text(text = "Security update is required for biometric authentication.")
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Text(text = "This version of Android does not support biometric authentication.")
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                Text(text = "Unable to determine biometric authentication status.")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Text(text = "No biometric data is enrolled.")
            }
        }
    }
}