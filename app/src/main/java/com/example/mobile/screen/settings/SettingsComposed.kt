package com.example.mobile.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.data.THEME_OPTION
import com.example.mobile.model.settings.SettingsViewModel

@Composable
fun Settings(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeOption = viewModel.themeOption.collectAsState()

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
            Text("Choose Theme", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            ThemeOptionItem(
                label = "System Default",
                selected = themeOption.value == THEME_OPTION.DEFAULT,
                onSelect = { viewModel.setThemeOption(THEME_OPTION.DEFAULT) }
            )

            ThemeOptionItem(
                label = "Light Theme",
                selected = themeOption.value == THEME_OPTION.LIGHT,
                onSelect = { viewModel.setThemeOption(THEME_OPTION.LIGHT) }
            )

            ThemeOptionItem(
                label = "Dark Theme",
                selected = themeOption.value == THEME_OPTION.DARK,
                onSelect = { viewModel.setThemeOption(THEME_OPTION.DARK) }
            )
        }
    }
}

@Composable
fun ThemeOptionItem(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}