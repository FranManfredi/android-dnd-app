package com.example.mobile.screen.weapons

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.data.Weapon
import com.example.mobile.model.weapon.WeaponViewModel
import com.example.mobile.ui.theme.orange
import com.example.mobile.R

@Composable
fun Weapons(viewModel: WeaponViewModel = hiltViewModel()) {
    val weaponList by viewModel.weaponsList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getWeapons()
    }

    // Show loading spinner if loading
    if (loadingState && weaponList.isEmpty()) {
        CircularProgressIndicator()
    }

    // Trigger a Toast when an error occurs
    LaunchedEffect(errorState) {
        errorState?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Display the list of weapons
    WeaponList(weaponList)
}

@Composable
fun WeaponList(weaponList: List<Weapon>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(weaponList) { weapon ->
            WeaponCard(weapon)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8dp)))
        }
    }
}

@Composable
fun WeaponCard(weapon: Weapon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_8dp)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius_8dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_16dp))
        ) {
            // Weapon Name
            Text(
                text = weapon.name,
                style = MaterialTheme.typography.headlineLarge, // Headline style for name
                fontWeight = FontWeight.Bold,
                color = orange
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8dp)))

            // Weapon Damage and To Hit in a Row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(R.string.weapon_damage)}: ${weapon.damage}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${stringResource(R.string.weapon_to_hit)}: +${weapon.to_hit}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8dp)))

            // Weapon Damage Type
            Text(
                text = "${stringResource(R.string.weapon_damage_type)}: ${weapon.damage_type}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8dp)))

            // Weapon Description
            Text(
                text = "${stringResource(R.string.weapon_description)}: ${weapon.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
