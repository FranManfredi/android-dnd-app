package com.example.mobile.screen.spells

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.data.Spell
import com.example.mobile.model.spell.SpellViewModel
import com.example.mobile.ui.theme.orange
import com.example.mobile.R

@Composable
fun Spells(viewModel: SpellViewModel = hiltViewModel()) {
    val spellList by viewModel.spellList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getSpells()
    }

    // Show loading spinner if loading
    if (loadingState && spellList.isEmpty()) {
        CircularProgressIndicator()
    }

    // Trigger a Toast when an error occurs
    LaunchedEffect(errorState) {
        errorState?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Display the list of spells
    SpellList(spellList)
}

@Composable
fun SpellList(spellList: List<Spell>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(spellList) { spell ->
            SpellCard(spell)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SpellCard(spell: Spell) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Spell Name
            Text(
                text = spell.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = orange
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Spell Level and Casting Time
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(id = R.string.spell_level)}: ${spell.level}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${stringResource(id = R.string.spell_casting_time)}: ${spell.casting_time}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "${stringResource(id = R.string.spell_description)}: ${spell.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
