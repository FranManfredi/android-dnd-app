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
import com.example.mobile.model.item.ItemViewModel
import kotlinx.coroutines.launch
import com.example.mobile.R

@Composable
fun ItemForm(nav: NavHostController, viewModel: ItemViewModel = hiltViewModel()) {
    var itemName by remember { mutableStateOf("") }
    var itemType by remember { mutableStateOf("") }
    var itemCharges by remember { mutableStateOf("") }
    var itemRecharge by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(stringResource(id = R.string.create_custom_item), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Item Name TextField
        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text(stringResource(id = R.string.item_name)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Type TextField
        OutlinedTextField(
            value = itemType,
            onValueChange = { itemType = it },
            label = { Text(stringResource(id = R.string.item_type)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Charges TextField
        OutlinedTextField(
            value = itemCharges,
            onValueChange = { itemCharges = it },
            label = { Text(stringResource(id = R.string.item_charges)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Recharge TextField
        OutlinedTextField(
            value = itemRecharge,
            onValueChange = { itemRecharge = it },
            label = { Text(stringResource(id = R.string.item_recharge)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Item Description TextField
        OutlinedTextField(
            value = itemDescription,
            onValueChange = { itemDescription = it },
            label = { Text(stringResource(id = R.string.item_description)) }
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
            Text(stringResource(id = R.string.submit_item))
        }
    }
}