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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.orange

@Composable
fun TopBar(
    onNavigateToSettings: () -> Unit,
    onNavigateToCreator: () -> Unit,
    onNavigateToHome: () -> Unit,
    title: String,
    type: TOPBAR_TYPES
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        ButtonWithIcon(onNavigate = { onNavigateToSettings() }, icon = Icons.Filled.Settings)
        Text(text = title, color = orange, style = TextStyle.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if(type == TOPBAR_TYPES.CREATOR) {
            ButtonWithIcon(onNavigate = { onNavigateToHome() }, icon = Icons.AutoMirrored.Rounded.ArrowBack)
        }
        else{
            ButtonWithIcon(onNavigate = { onNavigateToCreator() }, icon = Icons.Filled.AddCircle)
        }
    }
}

enum class TOPBAR_TYPES{
    NORMAL,
    CREATOR
}