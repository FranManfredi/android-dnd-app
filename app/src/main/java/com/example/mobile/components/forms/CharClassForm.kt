package com.example.mobile.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobile.R
import com.example.mobile.model.classes.CharClassViewModel
import kotlinx.coroutines.launch

@Composable
fun CharClassForm(nav: NavHostController, viewModel: CharClassViewModel = hiltViewModel()) {
    var className by remember { mutableStateOf("") }
    var hitDie by remember { mutableStateOf("") }
    var savingThrows by remember { mutableStateOf("") }
    var primaryAbility by remember { mutableStateOf("") }
    var classDescription by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_16dp)) // Use padding from dimens
    ) {
        Text(
            text = stringResource(id = R.string.create_custom_class),
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = dimensionResource(id = R.dimen.headline_large_text_size).value.sp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8dp)))

        // Class Name
        OutlinedTextField(
            value = className,
            onValueChange = { className = it },
            label = { Text(stringResource(id = R.string.class_name)) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8dp)))

        // Hit Die
        OutlinedTextField(
            value = hitDie,
            onValueChange = { hitDie = it },
            label = { Text(stringResource(id = R.string.class_hit_die)) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8dp)))

        // Saving Throws
        OutlinedTextField(
            value = savingThrows,
            onValueChange = { savingThrows = it },
            label = { Text(stringResource(id = R.string.class_saving_throws)) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8dp)))

        // Primary Ability
        OutlinedTextField(
            value = primaryAbility,
            onValueChange = { primaryAbility = it },
            label = { Text(stringResource(id = R.string.class_primary_ability)) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8dp)))

        // Class Description
        OutlinedTextField(
            value = classDescription,
            onValueChange = { classDescription = it },
            label = { Text(stringResource(id = R.string.class_description)) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8dp)))

        // Submit button
        Button(onClick = {
            coroutineScope.launch {
                viewModel.addClass(
                    name = className,
                    hitDie = hitDie,
                    description = classDescription,
                    savingThrows = savingThrows,
                    primaryAbility = primaryAbility
                )
                nav.popBackStack()
            }
        }) {
            Text(stringResource(id = R.string.submit_class))
        }
    }
}
