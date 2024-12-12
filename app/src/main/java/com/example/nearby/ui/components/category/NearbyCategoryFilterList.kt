package com.example.nearby.ui.components.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nearby.data.model.Category
import com.example.nearby.data.model.mock.mockCategories

@Composable
fun NearbyCategoryFilterList(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onSelectedCategoryChange: (Category) -> Unit
) {
    var selectedCategoryId by remember { mutableStateOf(categories.firstOrNull()?.id.orEmpty()) }

    LaunchedEffect (key1 = selectedCategoryId) {
        val selectedCategoryOrNull = categories.find { it.id == selectedCategoryId }
        selectedCategoryOrNull?.let { onSelectedCategoryChange(it) }
    }

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = categories, key = { it.id }) {
            NearbyCategoryFilterChip(
                category = it,
                isSelected = it.id == selectedCategoryId,
                onClick = { isSelected ->
                    if (isSelected) {
                        selectedCategoryId = it.id
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun NearbyCategoryFilterListPreview() {
    NearbyCategoryFilterList(
        modifier = Modifier.fillMaxWidth(),
        categories = mockCategories,
        onSelectedCategoryChange = {}
    )
}