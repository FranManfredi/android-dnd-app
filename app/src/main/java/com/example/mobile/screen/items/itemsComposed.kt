package com.example.mobile.screen.items

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.data.Item
import com.example.mobile.model.item.ItemViewModel
import com.example.mobile.ui.theme.orange
import androidx.compose.ui.res.stringResource
import com.example.mobile.R

@Composable
fun Items(viewModel: ItemViewModel = hiltViewModel()) {
    val itemList by viewModel.itemList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getItems()
    }

    // Show loading spinner if loading
    if (loadingState && itemList.isEmpty()) {
        CircularProgressIndicator()
    }

    // Trigger a Toast when an error occurs
    LaunchedEffect(errorState) {
        errorState?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Display the list of items
    ItemList(itemList)
}

@Composable
fun ItemList(itemList: List<Item>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(itemList) { item ->
            ItemCard(item)
            Spacer(modifier = Modifier.height(8.dp)) // Add some spacing between items
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp) // Rounded corners for a softer look
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Item Name
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineLarge, // Headline style for name
                fontWeight = FontWeight.Bold,
                color = orange
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Item Type and Charges in a Row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(id = R.string.item_type)}: ${item.type}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${stringResource(id = R.string.item_charges)}: ${item.charges}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "${stringResource(id = R.string.item_description)}: ${item.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Recharge Information (if any)
            if (item.recharge.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${stringResource(id = R.string.item_recharge)}: ${item.recharge}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
