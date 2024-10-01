package com.example.mobile.screen.compendium

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
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

    val navigation: List<(NavHostController) -> Unit> = listOf(
        { navigateWithOptions(MobileScreen.Weapons.name) },
        { navigateWithOptions(MobileScreen.Spells.name) },
        { navigateWithOptions(MobileScreen.Items.name) },
        { navigateWithOptions(MobileScreen.Classes.name) },
        { navigateWithOptions(MobileScreen.Races.name) }
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.compendium_padding))
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularPlusButton {
                navigateWithOptions(MobileScreen.CompendiumCreator.name)
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_large)))

            val buttons = listOf(
                R.string.weapons,
                R.string.spells,
                R.string.items,
                R.string.classes,
                R.string.races
            )

            buttons.forEachIndexed { index, textId ->
                CompendiumButton(text = stringResource(id = textId)) {
                    navigation[index](navController)
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_medium)))
            }
        }
    }
}

@Composable
fun CircularPlusButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_height)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.compendium_padding))
            .height(dimensionResource(id = R.dimen.button_height)),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(orange)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add),
            tint = Color.White,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
        )
        Text(
            text = stringResource(id = R.string.addCompendium),
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.button_text_size).value.sp,
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
            .padding(horizontal = dimensionResource(id = R.dimen.compendium_padding)),
        colors = ButtonDefaults.buttonColors(orange)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.button_text_size).value.sp,
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
