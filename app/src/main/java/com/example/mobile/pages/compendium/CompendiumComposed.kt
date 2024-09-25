package com.example.mobile.pages.compendium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    // List of lambda functions for navigation, each corresponding to a different screen
    val navigation: List<(NavHostController) -> Unit> = listOf(
        { navController.navigate(MobileScreen.Weapons.name) }, // Navigate to Weapons screen
        { navController.navigate(MobileScreen.Spells.name) },  // Navigate to Spells screen
        { navController.navigate(MobileScreen.Armour.name) },  // Navigate to Armors screen
        { navController.navigate(MobileScreen.Items.name) },   // Navigate to Items screen
        { navController.navigate(MobileScreen.Classes.name) }, // Navigate to Classes screen
        { navController.navigate(MobileScreen.Races.name) }    // Navigate to Races screen
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // List of button labels
            val buttons = listOf(
                R.string.weapons,
                R.string.spells,
                R.string.armors,
                R.string.items,
                R.string.classes,
                R.string.races
            )

            // Iterate over both the buttons and navigations simultaneously
            buttons.forEachIndexed { index, textId ->
                CompendiumButton(text = stringResource(id = textId)) {
                    // Invoke the corresponding lambda for navigation when the button is clicked
                    navigation[index](navController)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
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
