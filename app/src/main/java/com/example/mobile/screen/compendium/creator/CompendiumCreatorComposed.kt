package com.example.mobile.screen.compendium.creator

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.example.mobile.components.forms.CharClassForm
import com.example.mobile.components.forms.ItemForm
import com.example.mobile.components.forms.RaceForm
import com.example.mobile.components.forms.SpellForm
import com.example.mobile.components.forms.WeaponForm
import com.example.mobile.ui.theme.orange
import com.example.mobile.R

@Composable
fun CompendiumCreator(nav: NavHostController) {
    val formTypes = listOf("Item", "Weapon", "Spell", "Class", "Race")
    var selectedForm by remember { mutableStateOf(formTypes[0]) } // Initially select the first form type

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.creator_padding)), // Use dimen resource
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Dropdown to select the form type
        Text(text = "Select the form type to create:", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height))) // Use dimen resource

        // Dropdown to select the form type
        DropdownMenu(selectedForm, formTypes) { newForm ->
            selectedForm = newForm // Update the selected form type
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.creator_padding))) // Use dimen resource

        // Show the appropriate form based on the selection
        when (selectedForm) {
            "Item" -> ItemForm(nav)
            "Weapon" -> WeaponForm(nav)
            "Spell" -> SpellForm(nav)
            "Class" -> CharClassForm(nav)
            "Race" -> RaceForm(nav)
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
                .border(dimensionResource(id = R.dimen.border_thickness), orange) // Use dimen resource
                .clickable { expanded = !expanded }
                .padding(dimensionResource(id = R.dimen.dropdown_padding)) // Use dimen resource
        ) {
            Text(text = selectedForm)
        }

        // Show the dropdown if expanded is true
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(dimensionResource(id = R.dimen.border_thickness), orange) // Use dimen resource
            ) {
                formTypes.forEach { formType ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onFormSelected(formType)
                                expanded = false
                            }
                            .padding(dimensionResource(id = R.dimen.dropdown_padding)) // Use dimen resource
                    ) {
                        Text(text = formType)
                    }
                }
            }
        }
    }
}
