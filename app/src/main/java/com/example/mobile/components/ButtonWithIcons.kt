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
import androidx.compose.ui.res.dimensionResource
import com.example.mobile.ui.theme.orange
import com.example.mobile.R

@Composable
fun ButtonWithIcon(
    onNavigate: () -> Unit,
    icon: ImageVector
) {
    Button(
        onClick = { onNavigate() },
        colors = ButtonDefaults.buttonColors(Color.Transparent), // No background color
        shape = CircleShape, // Circular shape
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_0dp)), // No padding
        elevation = null, // No shadow
        modifier = Modifier.size(dimensionResource(id = R.dimen.button_size_48dp)) // Button size
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add",
            tint = orange,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_32dp)) // Icon size
        )
    }
}
