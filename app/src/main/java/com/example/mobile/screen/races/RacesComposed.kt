package com.example.mobile.screen.races

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.R
import com.example.mobile.data.Race
import com.example.mobile.model.race.RaceViewModel
import com.example.mobile.ui.theme.orange

@Composable
fun Races(viewModel: RaceViewModel = hiltViewModel()) {
    val raceList by viewModel.raceList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getRaces()
    }

    // Show loading spinner if loading
    if (loadingState && raceList.isEmpty()) {
        CircularProgressIndicator()
    }

    // Trigger a Toast when an error occurs
    LaunchedEffect(errorState) {
        errorState?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Display the list of races
    RaceList(raceList)
}

@Composable
fun RaceList(raceList: List<Race>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(raceList) { race ->
            RaceCard(race)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RaceCard(race: Race) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Race Name
            Text(
                text = race.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = orange
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Size and Speed
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(id = R.string.race_size)}: ${race.size}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${stringResource(id = R.string.race_speed)}: ${race.speed}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "${stringResource(id = R.string.race_description)}: ${race.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
