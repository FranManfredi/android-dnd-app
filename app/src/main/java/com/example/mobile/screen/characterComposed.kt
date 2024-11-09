package com.example.mobile.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobile.data.Character
import com.example.mobile.data.CharacterWithDetails
import com.example.mobile.model.character.CharacterViewModel

@Composable
fun CharacterComposed(
    characterName: String,
    navController: NavController,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    // Observe the character data from the ViewModel
    val character by viewModel.getCharacterByName(characterName).collectAsState(initial = null)

    // If the character is null, show a loading or error message
    if (character == null) {
        Text(
            text = "Loading character data...",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else {
        CharacterDetails(character = character!!, navController = navController)
    }
}

@Composable
fun CharacterDetails(character: CharacterWithDetails, navController: NavController) {
    // Scrollable content for details
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header with character name and race
        Text(
            text = character.character.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Race: ${character.character.race ?: "Unknown"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Character Class
        Text(
            text = "Class",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        character.characterClasses?.forEach { charClass ->
            Text(text = "${charClass.name} (Level ${charClass.level})", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ability Scores
        Text(
            text = "Ability Scores",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        AbilityScoresSection(character)

        Spacer(modifier = Modifier.height(16.dp))

        // Traits
        Text(
            text = "Traits",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        character.traits?.forEach { trait ->
            Text(
                text = trait.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = trait.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AbilityScoresSection(character: CharacterWithDetails) {
    // Display each ability score in a row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AbilityScoreItem(label = "STR", value = character.baseStats.str ?: 0)
        AbilityScoreItem(label = "DEX", value = character.baseStats.dex ?: 0)
        AbilityScoreItem(label = "CON", value = character.baseStats.con ?: 0)
        AbilityScoreItem(label = "INT", value = character.baseStats.intelligence ?: 0)
        AbilityScoreItem(label = "WIS", value = character.baseStats.wis ?: 0)
        AbilityScoreItem(label = "CHA", value = character.baseStats.cha ?: 0)
    }
}

@Composable
fun AbilityScoreItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value.toString(), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}
