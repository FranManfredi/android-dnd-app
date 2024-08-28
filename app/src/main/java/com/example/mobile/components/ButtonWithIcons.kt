package com.example.mobile.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.orange

@Composable
fun ButtonWithIcon(
    onNavigate: () -> Unit,
    icon: ImageVector
){
    Button(
        onClick = { onNavigate() },
        colors = ButtonDefaults.buttonColors(Color.Transparent), // Sin color de fondo
        shape = CircleShape, // Forma redonda
        contentPadding = PaddingValues(0.dp), // Sin padding
        elevation = null, // Sin sombra
        modifier = Modifier.size(48.dp) // Ajusta el tamaño del botón
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add",
            tint = orange,
            modifier = Modifier.size(32.dp)
        )
    }
}
