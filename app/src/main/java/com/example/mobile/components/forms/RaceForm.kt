package com.example.mobile.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.example.mobile.model.race.RaceViewModel

@Composable
fun RaceForm(nav: NavHostController, viewModel: RaceViewModel = hiltViewModel()) {
    var raceName by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var speed by remember { mutableStateOf("") }
    var raceDescription by remember { mutableStateOf("") }
    var specialAbilities by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(stringResource(id = R.string.create_custom_race), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Race Name
        OutlinedTextField(
            value = raceName,
            onValueChange = { raceName = it },
            label = { Text(stringResource(id = R.string.race_name)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Race Size
        OutlinedTextField(
            value = size,
            onValueChange = { size = it },
            label = { Text(stringResource(id = R.string.race_size)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Race Speed
        OutlinedTextField(
            value = speed,
            onValueChange = { speed = it },
            label = { Text(stringResource(id = R.string.race_speed)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Special Abilities
        OutlinedTextField(
            value = specialAbilities,
            onValueChange = { specialAbilities = it },
            label = { Text(stringResource(id = R.string.race_special_abilities)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Race Description
        OutlinedTextField(
            value = raceDescription,
            onValueChange = { raceDescription = it },
            label = { Text(stringResource(id = R.string.race_description)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Submit button
        Button(onClick = {
            coroutineScope.launch {
                viewModel.addRace(
                    name = raceName,
                    size = size,
                    speed = speed.toIntOrNull() ?: 0,
                    description = raceDescription,
                    specialAbilities = specialAbilities
                )
                nav.popBackStack()
            }
        }) {
            Text(stringResource(id = R.string.submit_race))
        }
    }
}