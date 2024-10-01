package com.example.mobile.screen.compendium.creator

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobile.data.DungeonsHelperDatabase
import com.example.mobile.data.Item
import com.example.mobile.model.item.ItemViewModel
import com.example.mobile.ui.theme.orange
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@Composable
fun CompendiumCreator(nav: NavHostController) {
    val formTypes = listOf("Item", "Weapon", "Spell", "Class", "Race")
    var selectedForm by remember { mutableStateOf(formTypes[0]) } // Initially select the first form type

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Dropdown to select the form type
        Text(text = "Select the form type to create:", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown to select the form type
        DropdownMenu(selectedForm, formTypes) { newForm ->
            selectedForm = newForm // Update the selected form type
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show the appropriate form based on the selection
        when (selectedForm) {
            "Item" -> ItemForm(nav)
            "Weapon" -> WeaponForm()
            "Spell" -> SpellForm()
            "Class" -> ClassForm()
            "Race" -> RaceForm()
        }
    }
}

@Composable
fun ItemForm(nav: NavHostController, viewModel: ItemViewModel = hiltViewModel()) {
    var itemName by remember { mutableStateOf("") }
    var itemType by remember { mutableStateOf("") }
    var itemCharges by remember { mutableStateOf("") }
    var itemRecharge by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column {
        Text("Create a Custom Item", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Item Name TextField
        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Type TextField
        OutlinedTextField(
            value = itemType,
            onValueChange = { itemType = it },
            label = { Text("Item Type") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Charges TextField
        OutlinedTextField(
            value = itemCharges,
            onValueChange = { itemCharges = it },
            label = { Text("Item Charges") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Recharge TextField
        OutlinedTextField(
            value = itemRecharge,
            onValueChange = { itemRecharge = it },
            label = { Text("Item Recharge") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Description TextField
        OutlinedTextField(
            value = itemDescription,
            onValueChange = { itemDescription = it },
            label = { Text("Item Description") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Submit button
        Button(onClick = {
            coroutineScope.launch {
                // Insert the item via the ItemViewModel
                viewModel.addItem(
                    name = itemName,
                    type = itemType,
                    charges = itemCharges,
                    recharge = itemRecharge,
                    description = itemDescription
                )
                // Optionally navigate back after submission
                nav.popBackStack()
            }
        }) {
            Text("Submit Item")
        }
    }
}

@Composable
fun WeaponForm() {
    // Custom weapon form elements
    Column {
        Text("Create a Custom Weapon", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Weapon Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Weapon Type") })
        Spacer(modifier = Modifier.height(8.dp))
        // Add more fields as needed for Weapon
        Button(onClick = { /* Handle submit */ }) {
            Text("Submit Weapon")
        }
    }
}

@Composable
fun SpellForm() {
    // Custom spell form elements
    Column {
        Text("Create a Custom Spell", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Spell Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Spell Description") })
        Spacer(modifier = Modifier.height(8.dp))
        // Add more fields as needed for Spell
        Button(onClick = { /* Handle submit */ }) {
            Text("Submit Spell")
        }
    }
}

@Composable
fun ClassForm() {
    // Custom class form elements
    Column {
        Text("Create a Custom Class", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Class Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Class Abilities") })
        Spacer(modifier = Modifier.height(8.dp))
        // Add more fields as needed for Class
        Button(onClick = { /* Handle submit */ }) {
            Text("Submit Class")
        }
    }
}

@Composable
fun RaceForm() {
    // Custom race form elements
    Column {
        Text("Create a Custom Race", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Race Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Race Traits") })
        Spacer(modifier = Modifier.height(8.dp))
        // Add more fields as needed for Race
        Button(onClick = { /* Handle submit */ }) {
            Text("Submit Race")
        }
    }
}

@Composable
fun DropdownMenu(
    selectedForm: String,
    formTypes: List<String>,
    onFormSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // This is the clickable text that will open the dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, orange)
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            Text(text = selectedForm)
        }

        // Show the dropdown if expanded is true
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, orange)
            ) {
                formTypes.forEach { formType ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onFormSelected(formType)
                                expanded = false
                            }
                            .padding(16.dp)
                    ) {
                        Text(text = formType)
                    }
                }
            }
        }
    }
}
