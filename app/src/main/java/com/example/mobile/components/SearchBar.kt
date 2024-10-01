package com.example.mobile.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        placeholder = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                fontSize = dimensionResource(id = R.dimen.font_size_18sp).value.sp, // Using the font size from dimens
                fontWeight = FontWeight.Normal, // Adjust font weight
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_8dp)), // Using the corner radius from dimens
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.search_bar_padding_start),
                end = dimensionResource(id = R.dimen.search_bar_padding_end)
            )
            .height(dimensionResource(id = R.dimen.search_bar_height)), // Using the height from dimens
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent, // Transparent focus indicator
            unfocusedIndicatorColor = Color.Transparent, // Transparent unfocused indicator
        ),
    )
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(query = "", onQueryChanged = {}, onSearch = { /*TODO*/ }, text = "Buscar")
}
