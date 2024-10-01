package com.example.mobile.screen.compendium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.R
import com.example.mobile.navigation.MobileScreen
import com.example.mobile.ui.theme.orange

@Composable
fun Compendium(navController: NavHostController) {
    // Helper function to navigate with custom options
    val navigateWithOptions: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // List of lambda functions for navigation, each corresponding to a different screen
    val navigation: List<(NavHostController) -> Unit> = listOf(
        { navigateWithOptions(MobileScreen.Weapons.name) },  // Navigate to Weapons screen
        { navigateWithOptions(MobileScreen.Spells.name) },   // Navigate to Spells screen
        { navigateWithOptions(MobileScreen.Items.name) },    // Navigate to Items screen
        { navigateWithOptions(MobileScreen.Classes.name) },  // Navigate to Classes screen
        { navigateWithOptions(MobileScreen.Races.name) }     // Navigate to Races screen
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularPlusButton {
                navigateWithOptions(MobileScreen.CompendiumCreator.name)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // List of button labels
            val buttons = listOf(
                R.string.weapons,
                R.string.spells,
                R.string.items,
                R.string.classes,
                R.string.races
            )

            // Iterate over both the buttons and navigations simultaneously
            buttons.forEachIndexed { index, textId ->
                CompendiumButton(text = stringResource(id = textId)) {
                    navigation[index](navController)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CircularPlusButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(128.dp), // Size of the circular button
        contentPadding = PaddingValues(0.dp), // Remove default padding to center the icon better
        colors = ButtonDefaults.buttonColors(orange)
        ) {
        Icon(
            imageVector = Icons.Default.Add, // The plus sign icon
            contentDescription = stringResource(id = R.string.add),
            tint = Color.White, // White color for the plus sign
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = stringResource(id = R.string.addCompendium),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CompendiumButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(orange)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Compendium(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun PreviewCompendium() {
    MainScreen() // Preview the main screen, passing the NavController
}
