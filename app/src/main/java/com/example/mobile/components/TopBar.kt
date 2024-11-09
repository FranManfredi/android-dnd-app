package com.example.mobile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.ui.theme.orange

@Composable
fun TopBar(
    onNavigateToSettings: () -> Unit,
    onNavigateToCreator: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToCompendium: () -> Unit,
    title: String,
    type: TOPBAR_TYPES
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.topbar_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.topbar_padding_vertical)
            )
    ) {
        ButtonWithIcon(onNavigate = { onNavigateToSettings() }, icon = Icons.Filled.Settings)
        when (type) {
            TOPBAR_TYPES.CHARACTER -> {
                Text(
                    text = "Character",
                    color = orange,
                    style = TextStyle.Default,
                    fontSize = dimensionResource(id = R.dimen.font_size_20sp).value.sp, // Use dimension for font size
                    fontWeight = FontWeight.Bold
                )
            }
            else -> {
                Text(
                    text = title,
                    color = orange,
                    style = TextStyle.Default,
                    fontSize = dimensionResource(id = R.dimen.font_size_20sp).value.sp, // Use dimension for font size
                    fontWeight = FontWeight.Bold
                )
            }
        }

        when (type) {
            TOPBAR_TYPES.CREATOR, TOPBAR_TYPES.CHARACTER -> {
                ButtonWithIcon(onNavigate = { onNavigateToHome() }, icon = Icons.AutoMirrored.Rounded.ArrowBack)
            }
            TOPBAR_TYPES.COMPENDIUM -> {
                ButtonWithIcon(onNavigate = { onNavigateToCompendium() }, icon = Icons.AutoMirrored.Rounded.ArrowBack)
            }
            else -> {
                ButtonWithIcon(onNavigate = { onNavigateToCreator() }, icon = Icons.Filled.AddCircle)
            }
        }
    }
}

enum class TOPBAR_TYPES {
    NORMAL,
    CREATOR,
    COMPENDIUM,
    CHARACTER
}
