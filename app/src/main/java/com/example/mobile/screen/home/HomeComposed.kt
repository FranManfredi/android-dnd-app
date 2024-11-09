package com.example.mobile.screen.home

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.components.SearchBar
import com.example.mobile.data.Character
import com.example.mobile.model.character.CharacterViewModel
import com.example.mobile.navigation.MobileScreen

@Composable
fun Home(
    navController: NavController,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    // Trigger data load when Home is entered
    LaunchedEffect(Unit) {
        viewModel.loadCharacters() // Reload characters on each entry
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
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
            .padding(vertical = 8.dp) // Add some spacing between cards
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.medium // Rounded corners for a smoother look
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant) // Subtle background color
                .padding(16.dp)
        ) {
            // Decorative Icon or Image Placeholder
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Character Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                // Name Text with Larger Font and Bold Style
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1 // Limit name to one line if it's too long
                )

                // Race Text with Smaller Font and Muted Color
                Text(
                    text = "Race: ${character.race ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

