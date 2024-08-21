package com.example.mobile.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile.R
import com.example.mobile.components.SearchBar
import com.example.mobile.components.TopBar
import com.example.mobile.navigation.MobileScreen

@Preview
@Composable
fun PreviewHome() {
    Home()
}

@Composable
fun Home() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            SearchBar(
                query = "",
                onQueryChanged = {},
                onSearch = { /*TODO*/ },
                text = stringResource( id = R.string.search_var)
            )
        }
    }
}
