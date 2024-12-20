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

import androidx.compose.ui.res.dimensionResource

import androidx.compose.ui.res.stringResource
import com.example.mobile.navigation.getScreenTitle

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
                    else -> isSystemInDarkTheme()
                }

                MobileTheme(darkTheme = isDarkTheme) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = dimensionResource(id = R.dimen.dp_35)),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = @Composable {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                navBackStackEntry?.destination?.route?.let { route ->
                                    val topBarType = getTopBarType(route)
                                    TopBar(
                                        title = getScreenTitle(route),
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
                AuthenticationMessage(stringResource(id = R.string.biometric_error_no_hardware))
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                AuthenticationMessage(stringResource(id = R.string.biometric_error_hw_unavailable))
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                AuthenticationMessage(stringResource(id = R.string.biometric_error_security_update_required))
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                AuthenticationMessage(stringResource(id = R.string.biometric_error_unsupported))
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                AuthenticationMessage(stringResource(id = R.string.biometric_status_unknown))
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                AuthenticationMessage(stringResource(id = R.string.biometric_error_none_enrolled))
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
            popUpTo(0) { inclusive = true } // Clears the backstack up to the root
        }
    }
}