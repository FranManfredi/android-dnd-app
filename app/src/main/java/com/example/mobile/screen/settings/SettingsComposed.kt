package com.example.mobile.screen.settings

import SettingsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Settings(
    themeViewModel: SettingsViewModel = hiltViewModel() // Inject ViewModel
) {
    // Collect the current theme state as a State<Boolean>
    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { themeViewModel.toggleTheme() }
            ) {
                Text(text = if (isDarkTheme.value) "Switch to Light Theme" else "Switch to Dark Theme")
            }
        }
    }
}