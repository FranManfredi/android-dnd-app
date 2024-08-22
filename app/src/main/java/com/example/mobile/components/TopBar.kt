package com.example.mobile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.orange

@Composable
fun TopBar(
    onNavigateToSettings: () -> Unit,
    onNavigateToCreator: () -> Unit,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = { onNavigateToSettings() },
            colors = ButtonDefaults.buttonColors(Color.Transparent), // Sin color de fondo
            shape = CircleShape, // Forma redonda
            contentPadding = PaddingValues(0.dp), // Sin padding
            elevation = null, // Sin sombra
            modifier = Modifier.size(48.dp) // Ajusta el tamaño del botón
        ){
            Icon(imageVector = Icons.Filled.Settings , contentDescription = "Settings",
                tint = orange, modifier = Modifier.size( 32.dp ))
        }
        Text(text = title, color = orange, style = TextStyle.Default, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Button(
            onClick = { onNavigateToCreator() },
            colors = ButtonDefaults.buttonColors(Color.Transparent), // Sin color de fondo
            shape = CircleShape, // Forma redonda
            contentPadding = PaddingValues(0.dp), // Sin padding
            elevation = null, // Sin sombra
            modifier = Modifier.size(48.dp) // Ajusta el tamaño del botón
        ){
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Add",
                tint = orange, modifier = Modifier.size( 32.dp ))
        }
    }
}