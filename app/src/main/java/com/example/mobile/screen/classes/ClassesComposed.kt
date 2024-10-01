package com.example.mobile.screen.classes

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.R
import com.example.mobile.data.CharClass
import com.example.mobile.model.classes.CharClassViewModel
import com.example.mobile.ui.theme.orange

@Composable
fun Classes(viewModel: CharClassViewModel = hiltViewModel()) {
    val classList by viewModel.classList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getClasses()
    }

    // Show loading spinner if loading
    if (loadingState && classList.isEmpty()) {
        CircularProgressIndicator()
    }

    // Trigger a Toast when an error occurs
    LaunchedEffect(errorState) {
        errorState?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Display the list of classes
    ClassList(classList)
}

@Composable
fun ClassList(classList: List<CharClass>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(classList) { charClass ->
            ClassCard(charClass)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height))) // Use dimen resource
        }
    }
}

@Composable
fun ClassCard(charClass: CharClass) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.card_padding)), // Use dimen resource
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)) // Use dimen resource
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.column_padding)) // Use dimen resource
        ) {
            // Class Name
            Text(
                text = charClass.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = orange
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height))) // Use dimen resource

            // Hit Die and Primary Ability
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(id = R.string.class_hit_die)}: ${charClass.hit_die}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${stringResource(id = R.string.class_primary_ability)}: ${charClass.primary_ability}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height))) // Use dimen resource

            // Description
            Text(
                text = "${stringResource(id = R.string.class_description)}: ${charClass.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
