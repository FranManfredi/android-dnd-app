package com.example.mobile.screen.creator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.model.character.CharacterViewModel
import com.example.mobile.data.CharacterClass
import com.example.mobile.data.BaseStats
import com.example.mobile.data.CharacterProficiency
import com.example.mobile.data.Trait
import com.example.mobile.data.CharacterHp

@Composable
fun Creator(
    viewModel: CharacterViewModel = hiltViewModel(),
    navController: NavController
) {
    val defaultTraitName = stringResource(id = R.string.default_trait_name)
    val defaultTraitDescription = stringResource(id = R.string.default_trait_description)
    val errorFillRequiredFields = stringResource(id = R.string.error_fill_required_fields)

    CreatorContent(
        viewModel = viewModel,
        navController = navController,
        defaultTraitName = defaultTraitName,
        defaultTraitDescription = defaultTraitDescription,
        errorFillRequiredFields = errorFillRequiredFields
    )
}

@Composable
fun CreatorContent(
    viewModel: CharacterViewModel,
    navController: NavController,
    defaultTraitName: String,
    defaultTraitDescription: String,
    errorFillRequiredFields: String
) {
    var name by remember { mutableStateOf("") }
    var race by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("") }
    var strength by remember { mutableStateOf("") }
    var dexterity by remember { mutableStateOf("") }
    var constitution by remember { mutableStateOf("") }
    var intelligence by remember { mutableStateOf("") }
    var wisdom by remember { mutableStateOf("") }
    var charisma by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_16dp))
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_8dp))
        ) {
            Text(
                text = stringResource(id = R.string.character_creator_title),
                style = MaterialTheme.typography.headlineMedium
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.label_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = race,
                onValueChange = { race = it },
                label = { Text(stringResource(id = R.string.label_race)) },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = selectedClass,
                onValueChange = { selectedClass = it },
                label = { Text(stringResource(id = R.string.label_class)) },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(id = R.string.ability_scores),
                fontWeight = FontWeight.Bold
            )

            AbilityScoreField(label = stringResource(id = R.string.strength), value = strength) { strength = it }
            AbilityScoreField(label = stringResource(id = R.string.dexterity), value = dexterity) { dexterity = it }
            AbilityScoreField(label = stringResource(id = R.string.constitution), value = constitution) { constitution = it }
            AbilityScoreField(label = stringResource(id = R.string.intelligence), value = intelligence) { intelligence = it }
            AbilityScoreField(label = stringResource(id = R.string.wisdom), value = wisdom) { wisdom = it }
            AbilityScoreField(label = stringResource(id = R.string.charisma), value = charisma) { charisma = it }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

            Button(
                onClick = {
                    val baseStats = BaseStats(
                        str = strength.toIntOrNull() ?: 0,
                        dex = dexterity.toIntOrNull() ?: 0,
                        con = constitution.toIntOrNull() ?: 0,
                        intelligence = intelligence.toIntOrNull() ?: 0,
                        wis = wisdom.toIntOrNull() ?: 0,
                        cha = charisma.toIntOrNull() ?: 0
                    )

                    val characterClass = CharacterClass(
                        name = selectedClass,
                        level = 1,
                        characterName = name
                    )

                    val trait = Trait(
                        name = defaultTraitName,
                        description = defaultTraitDescription,
                        characterName = name
                    )

                    if (name.isNotBlank() && race.isNotBlank()) {
                        viewModel.addCharacter(
                            name = name,
                            race = race,
                            characterClasses = listOf(characterClass),
                            baseStats = baseStats,
                            proficiency = CharacterProficiency(
                                strSt = false, dexSt = false, conSt = false,
                                intSt = false, wisSt = false, chaSt = false,
                                acrobatics = false, animalHandling = false, arcana = false,
                                athletics = false, deception = false, history = false, insight = false,
                                intimidation = false, investigation = false, medicine = false,
                                nature = false, perception = false, performance = false,
                                persuasion = false, religion = false, sleightOfHand = false,
                                stealth = false, survival = false
                            ),
                            traits = listOf(trait),
                            hp = CharacterHp(totalHitPoints = 10, currentHitPoints = 10)
                        )
                        errorMessage = null
                        navController.popBackStack()
                    } else {
                        errorMessage = errorFillRequiredFields
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.save_character))
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_8dp))
                )
            }
        }
    }
}

@Composable
fun AbilityScoreField(label: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}