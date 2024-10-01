package com.example.mobile.components.forms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.mobile.R
import com.example.mobile.model.spell.SpellViewModel

@Composable
fun SpellForm(nav: NavHostController, viewModel: SpellViewModel = hiltViewModel()) {
    var spellName by remember { mutableStateOf("") }
    var spellLevel by remember { mutableStateOf("") }
    var spellDamage by remember { mutableStateOf("") }
    var damageType by remember { mutableStateOf("") }
    var castingTime by remember { mutableStateOf("") }
    var spellDescription by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text(stringResource(id = R.string.create_custom_spell), style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // Spell Name
            OutlinedTextField(
                value = spellName,
                onValueChange = { spellName = it },
                label = { Text(stringResource(id = R.string.spell_name)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Spell Level
            OutlinedTextField(
                value = spellLevel,
                onValueChange = { spellLevel = it },
                label = { Text(stringResource(id = R.string.spell_level)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Spell Damage
            OutlinedTextField(
                value = spellDamage,
                onValueChange = { spellDamage = it },
                label = { Text(stringResource(id = R.string.spell_damage)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Damage Type
            OutlinedTextField(
                value = damageType,
                onValueChange = { damageType = it },
                label = { Text(stringResource(id = R.string.spell_damage_type)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Casting Time
            OutlinedTextField(
                value = castingTime,
                onValueChange = { castingTime = it },
                label = { Text(stringResource(id = R.string.spell_casting_time)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Spell Description
            OutlinedTextField(
                value = spellDescription,
                onValueChange = { spellDescription = it },
                label = { Text(stringResource(id = R.string.spell_description)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Submit button
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.addSpell(
                        name = spellName,
                        level = spellLevel.toIntOrNull() ?: 0,
                        damage = spellDamage,
                        damageType = damageType,
                        description = spellDescription,
                        castingTime = castingTime
                    )
                    nav.popBackStack()
                }
            }) {
                Text(stringResource(id = R.string.submit_spell))
            }
        }
    }
}
