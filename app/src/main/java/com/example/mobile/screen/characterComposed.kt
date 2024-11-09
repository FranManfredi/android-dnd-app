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
import com.example.mobile.R
import com.example.mobile.data.CharacterWithDetails
import com.example.mobile.model.character.CharacterViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource

@Composable
fun CharacterComposed(
    characterName: String,
    navController: NavController,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val character by viewModel.getCharacterByName(characterName).collectAsState(initial = null)

    if (character == null) {
        Text(
            text = stringResource(id = R.string.loading_character_data),
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.dp_16))
    ) {
        Text(
            text = character.character.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dp_8))
        )
        Text(
            text = stringResource(id = R.string.race, character.character.race ?: stringResource(id = R.string.unknown)),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

        Text(
            text = stringResource(id = R.string.character_class),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dp_8))
        )
        character.characterClasses?.forEach { charClass ->
            Text(
                text = stringResource(id = R.string.class_level, charClass.name, charClass.level),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

        Text(
            text = stringResource(id = R.string.ability_scores),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dp_8))
        )
        AbilityScoresSection(character)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

        Text(
            text = stringResource(id = R.string.traits),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dp_8))
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