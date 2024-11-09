package com.example.mobile.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.R
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
                .padding(dimensionResource(id = R.dimen.dp_16)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.choose_theme),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_24)))

            ThemeOptionItem(
                label = stringResource(id = R.string.system_default),
                selected = themeOption.value == THEME_OPTION.DEFAULT,
                onSelect = { viewModel.setThemeOption(THEME_OPTION.DEFAULT) }
            )

            ThemeOptionItem(
                label = stringResource(id = R.string.light_theme),
                selected = themeOption.value == THEME_OPTION.LIGHT,
                onSelect = { viewModel.setThemeOption(THEME_OPTION.LIGHT) }
            )

            ThemeOptionItem(
                label = stringResource(id = R.string.dark_theme),
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
            .padding(vertical = dimensionResource(id = R.dimen.dp_8))
            .clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_8)))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}