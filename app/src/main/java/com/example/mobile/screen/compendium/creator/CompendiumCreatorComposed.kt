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
import com.example.mobile.components.forms.CharClassForm
import com.example.mobile.components.forms.ItemForm
import com.example.mobile.components.forms.RaceForm
import com.example.mobile.components.forms.SpellForm
import com.example.mobile.components.forms.WeaponForm
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
