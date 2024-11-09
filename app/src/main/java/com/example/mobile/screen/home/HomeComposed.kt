package com.example.mobile.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.components.SearchBar
import com.example.mobile.data.Character
import com.example.mobile.model.character.CharacterViewModel
import com.example.mobile.navigation.MobileScreen

import androidx.compose.ui.res.dimensionResource

@Composable
fun Home(
    navController: NavController,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    var query by remember { mutableStateOf("") }
    val allCharacters by viewModel.characterList.collectAsState()
    val filteredCharacters = allCharacters.filter { it.name.contains(query, ignoreCase = true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            SearchBar(
                query = query,
                onQueryChanged = { query = it },
                onSearch = { /* Optional search action on enter */ },
                text = stringResource(id = R.string.search_var)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dp_16)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_8))
            ) {
                items(filteredCharacters) { character ->
                    CharacterCard(character = character, onClick = {
                        navController.navigate("${MobileScreen.Character.name}/${character.name}")
                    })
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.dp_8))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.dp_6)),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(dimensionResource(id = R.dimen.dp_16))
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(id = R.string.character_icon),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dp_48))
                    .padding(end = dimensionResource(id = R.dimen.dp_16))
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )

                Text(
                    text = stringResource(id = R.string.character_race, character.race ?: stringResource(id = R.string.unknown_race)),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dp_4))
                )
            }
        }
    }
}