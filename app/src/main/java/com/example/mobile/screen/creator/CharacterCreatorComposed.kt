package com.example.mobile.screen.creator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

    val scrollState = rememberScrollState() // For making the content scrollable

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState), // Make the content scrollable
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Character Creator",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = race,
                onValueChange = { race = it },
                label = { Text("Race") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = selectedClass,
                onValueChange = { selectedClass = it },
                label = { Text("Class") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Ability Scores", fontWeight = FontWeight.Bold)

            AbilityScoreField(label = "Strength", value = strength) { strength = it }
            AbilityScoreField(label = "Dexterity", value = dexterity) { dexterity = it }
            AbilityScoreField(label = "Constitution", value = constitution) { constitution = it }
            AbilityScoreField(label = "Intelligence", value = intelligence) { intelligence = it }
            AbilityScoreField(label = "Wisdom", value = wisdom) { wisdom = it }
            AbilityScoreField(label = "Charisma", value = charisma) { charisma = it }

            Spacer(modifier = Modifier.height(16.dp))

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
                        characterName = name // Pass character name here
                    )

                    val trait = Trait(
                        name = "Brave",
                        description = "Never afraid",
                        characterName = name // Pass character name here
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
                        navController.popBackStack() // Navigate back to the previous screen
                    } else {
                        errorMessage = "Please fill in all required fields."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Character")
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp)
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
