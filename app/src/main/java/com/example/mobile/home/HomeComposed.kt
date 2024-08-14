package com.example.mobile.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile.components.ButtonWithIcons
import com.example.mobile.components.TopBar

data class HomeButton(
    val leadingIcon: ImageVector,
    val trailingIcon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

@Preview
@Composable
fun PreviewHome() {
    Home()
}

@Composable
fun Home() {

//    val buttons = listOf<HomeButton>(
//        HomeButton(Icons.Filled.Person, trailingIcon = Icons.Filled.KeyboardArrowRight, title = "Profile", onClick = {}),
//        HomeButton(Icons.Filled.ShoppingCart, trailingIcon = Icons.Filled.KeyboardArrowRight, title = "Cart", onClick = {})
//    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            TopBar(title = "Character List")
//            buttons.forEach { button ->
//                ButtonWithIcons(
//                    leadingIcon = button.leadingIcon,
//                    title = button.title,
//                    trailingIcon = button.trailingIcon
//                )
//            }
        }
    }
}
