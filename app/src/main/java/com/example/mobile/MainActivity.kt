package com.example.mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile.components.BottomBar
import com.example.mobile.components.TOPBAR_TYPES
import com.example.mobile.components.TopBar
import com.example.mobile.data.THEME_OPTION
import com.example.mobile.model.settings.SettingsViewModel
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
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                val themeOption by settingsViewModel.themeOption.collectAsState()

                val isDarkTheme = when (themeOption) {
                    THEME_OPTION.LIGHT -> false
                    THEME_OPTION.DARK -> true
                    else -> isSystemInDarkTheme()  // Use system default
                }

                MobileTheme(darkTheme = isDarkTheme) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 35.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                navBackStackEntry?.destination?.route?.let { route ->
                                    val topBarType = getTopBarType(route)
                                    TopBar(
                                        title = route,
                                        type = topBarType,
                                        onNavigateToSettings = { navigateTo(navController, MobileScreen.Settings.name) },
                                        onNavigateToCreator = { navigateTo(navController, MobileScreen.Creator.name) },
                                        onNavigateToHome = { navigateToHome(navController) },
                                        onNavigateToCompendium = { navigateTo(navController, MobileScreen.Compendium.name) },
                                    )
                                }
                            },
                            bottomBar = {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.destination?.route
                                if (currentRoute !in listOf(MobileScreen.Creator.name)) {
                                    BottomBar(
                                        selectedRoute = currentRoute ?: MobileScreen.Home.name,
                                        onNavigate = { screen -> navigateTo(navController, screen) }
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
    private fun BiometricAuthentication(
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
                if (!isAuthenticated) biometricAuthManager.authenticate(context, {}, onSuccess, {})
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                AuthenticationMessage("This device does not support biometric authentication.")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                AuthenticationMessage("Biometric authentication is currently unavailable.")
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                AuthenticationMessage("Security update is required for biometric authentication.")
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                AuthenticationMessage("This version of Android does not support biometric authentication.")
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                AuthenticationMessage("Unable to determine biometric authentication status.")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                AuthenticationMessage("No biometric data is enrolled.")
            }
        }
    }

    @Composable
    private fun AuthenticationMessage(message: String) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text(text = message)
        }
    }

    private fun getTopBarType(route: String): TOPBAR_TYPES = when {
        route == "Creator" -> TOPBAR_TYPES.CREATOR
        route in listOf("Weapons", "Spells", "Items", "Armor", "Classes", "Races", "CompendiumCreator") -> TOPBAR_TYPES.COMPENDIUM
        route.contains("Character/") -> TOPBAR_TYPES.CHARACTER
        else -> TOPBAR_TYPES.NORMAL
    }

    private fun navigateTo(navController: NavHostController, route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateToHome(navController: NavHostController) {
        navController.navigate(MobileScreen.Home.name) {
            popUpTo(MobileScreen.Home.name) { inclusive = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}