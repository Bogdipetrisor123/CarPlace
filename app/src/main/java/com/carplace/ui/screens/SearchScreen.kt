package com.carplace.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.carplace.R
import com.carplace.ui.viewmodel.FilterCategory
import com.carplace.ui.viewmodel.FilterOption
import com.carplace.ui.viewmodel.SearchUiState
import com.carplace.ui.viewmodel.SearchViewModel

@Composable
internal fun SearchRoute(
    onSearchClick: () -> Unit,
    viewModel: SearchViewModel
) {
    val uiState = viewModel.uiState
    SearchScreen(
        uiState,
        onSearchClick = {
            viewModel.onSearchPressed()
            onSearchClick.invoke()
        },
        onFilterClick = viewModel::changeDialogState,
        dismissDialog = viewModel::dismissDialog,
        onSelectFilter = viewModel::onSelectFilter,
        onFilterValueChange = viewModel::onFilterValueChange
    )
}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onSearchClick: () -> Unit,
    onFilterClick: (openDialog: Boolean, selectedFilter: FilterCategory) -> Unit,
    dismissDialog: (FilterCategory) -> Unit,
    onSelectFilter: (FilterOption) -> Unit,
    onFilterValueChange: (onlyMinValue: Boolean, value: Int, filterCategory: FilterCategory) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                SearchFilterItem(
                    filterCategory = FilterCategory.MAKE,
                    values = uiState.chosenFilters.makes.ifEmpty { emptyList() },
                    onFilterItemClick = onFilterClick
                )
                if (uiState.chosenFilters.makes.size == 1) {
                    SearchFilterItem(
                        filterCategory = FilterCategory.MODEL,
                        values = uiState.chosenFilters.models,
                        onFilterItemClick = onFilterClick
                    )
                }
                SearchFilterItem(
                    filterCategory = FilterCategory.CATEGORY,
                    values = uiState.chosenFilters.categories,
                    onFilterItemClick = onFilterClick
                )
                SearchFilterItemWithoutDialog(
                    filterCategory = FilterCategory.MILEAGE,
                    minValue = uiState.chosenFilters.minMileage,
                    maxValue = uiState.chosenFilters.maxMileage,
                    onFilterValueChange = onFilterValueChange
                )
                SearchFilterItemWithoutDialog(
                    filterCategory = FilterCategory.FIRST_REGISTRATION,
                    minValue = uiState.chosenFilters.minFirstRegistration,
                    maxValue = uiState.chosenFilters.maxFirstRegistration,
                    onFilterValueChange = onFilterValueChange
                )
                SearchFilterItemWithoutDialog(
                    filterCategory = FilterCategory.PRICE,
                    minValue = uiState.chosenFilters.minPrice,
                    maxValue = uiState.chosenFilters.maxPrice,
                    onFilterValueChange = onFilterValueChange
                )
                SearchFilterItem(
                    filterCategory = FilterCategory.LOCATION,
                    values = uiState.chosenFilters.locations,
                    onFilterItemClick = onFilterClick
                )
                SearchFilterItem(
                    filterCategory = FilterCategory.FUEL_TYPE,
                    values = uiState.chosenFilters.fuelTypes,
                    onFilterItemClick = onFilterClick
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //enabled = uiState.chosenFilters.make.isNotEmpty(),
            onClick = onSearchClick
        ) {
            Text(text = "Search", textAlign = TextAlign.Center)
        }
        if (uiState.openDialog) {
            FilterDialog(
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onSelectFilter = onSelectFilter,
                dismissDialog = dismissDialog
            )
        }
    }
}

@Composable
fun SearchFilterItem(
    filterCategory: FilterCategory,
    values: List<String>,
    onFilterItemClick: (openDialog: Boolean, selectedFilter: FilterCategory) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
        text = filterCategory.getFilterName(),
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
    )
    Row(
        modifier = Modifier
            .background(color = Color.LightGray.copy(alpha = 0.3f))
            .padding(vertical = 12.dp)
            .fillMaxWidth()
            .clickable { onFilterItemClick(true, filterCategory) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
                .testTag(filterCategory.getFilterName()),
            text = if (values.isNotEmpty()) values.joinToString(", ") else "Choose",
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = "",
            modifier = Modifier.padding(end = 8.dp)
        )
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterItemWithoutDialog(
    filterCategory: FilterCategory,
    minValue: Int,
    maxValue: Int,
    onFilterValueChange: (onlyMinValue: Boolean, value: Int, filterCategory: FilterCategory) -> Unit,
) {
    Text(
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
        text = filterCategory.getFilterName(),
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
    )
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.weight(1f).testTag("${filterCategory.getFilterName()} min"),
            value = if (minValue != 0 ) { minValue.toString() } else {""},
            placeholder = { Text(text = "Min") },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            onValueChange = { onFilterValueChange(true, it.toIntOrNull() ?: 0 , filterCategory) })
        TextField(
            modifier = Modifier.weight(1f).testTag("${filterCategory.getFilterName()} max"),
            value = if (maxValue != 0 ) { maxValue.toString() } else {""},
            placeholder = { Text(text = "Max") },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            onValueChange = { onFilterValueChange(false, it.toIntOrNull() ?: 0, filterCategory) })
    }
}

@Composable
fun FilterDialog(
    modifier: Modifier,
    uiState: SearchUiState,
    onSelectFilter: (FilterOption) -> Unit,
    dismissDialog: (FilterCategory) -> Unit
) {
    val options = when (uiState.currentSelectedFilter) {
        FilterCategory.MAKE -> uiState.makes
        FilterCategory.MODEL -> uiState.models
        FilterCategory.CATEGORY -> uiState.categories
        FilterCategory.LOCATION -> uiState.locations
        FilterCategory.FUEL_TYPE -> uiState.fuelTypes
        else -> emptyList()
    }

    Dialog(
        onDismissRequest = { dismissDialog(uiState.currentSelectedFilter) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        text = uiState.currentSelectedFilter.getFilterName(),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                items(options) { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = modifier.clickable { onSelectFilter(option) },
                            text = option.option,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Checkbox(
                            checked = option.isSelected,
                            onCheckedChange = { onSelectFilter(option) }
                        )
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = true,
                onClick = { dismissDialog(uiState.currentSelectedFilter) }
            ) {
                Text(text = "Done", textAlign = TextAlign.Center)
            }
        }
    }
}