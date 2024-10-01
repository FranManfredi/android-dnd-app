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
import com.example.mobile.R
import com.example.mobile.model.weapon.WeaponViewModel
import kotlinx.coroutines.launch

@Composable
fun WeaponForm(nav: NavHostController, viewModel: WeaponViewModel = hiltViewModel()) {
    var weaponName by remember { mutableStateOf("") }
    var weaponDamage by remember { mutableStateOf("") }
    var weaponToHit by remember { mutableStateOf("") }
    var weaponDamageType by remember { mutableStateOf("") }
    var weaponDescription by remember { mutableStateOf("") }
    var weaponImageUrl by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text(stringResource(id = R.string.create_weapon), style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weaponName,
                onValueChange = { weaponName = it },
                label = { Text(stringResource(id = R.string.weapon_name)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weaponDamage,
                onValueChange = { weaponDamage = it },
                label = { Text(stringResource(id = R.string.weapon_damage)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weaponToHit,
                onValueChange = { weaponToHit = it },
                label = { Text(stringResource(id = R.string.weapon_to_hit)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weaponDamageType,
                onValueChange = { weaponDamageType = it },
                label = { Text(stringResource(id = R.string.weapon_damage_type)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weaponImageUrl,
                onValueChange = { weaponImageUrl = it },
                label = { Text(stringResource(id = R.string.weapon_image_url)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weaponDescription,
                onValueChange = { weaponDescription = it },
                label = { Text(stringResource(id = R.string.weapon_description)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                coroutineScope.launch {
                    viewModel.addWeapon(
                        name = weaponName,
                        damage = weaponDamage,
                        to_hit = weaponToHit.toInt(),
                        damage_type = weaponDamageType,
                        description = weaponDescription,
                        image_url = weaponImageUrl
                    )
                    nav.popBackStack()
                }
            }) {
                Text(stringResource(id = R.string.submit_weapon))
            }
        }
    }
}
