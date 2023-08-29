package com.carplace.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.carplace.ui.viewmodel.SearchUiState
import com.carplace.ui.viewmodel.SearchViewModel
import com.carplace.R

@Composable
internal fun SearchedCarsRoute(
    onBackClick: () -> Unit,
    onCarClick: (Int) -> Unit,
    viewModel: SearchViewModel
) {
    SearchedCarsScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick,
        onCarClick = onCarClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchedCarsScreen(
    uiState: SearchUiState,
    onBackClick: () -> Unit,
    onCarClick: (Int) -> Unit,
) {
    if (uiState.isInitComplete) {
        Column {
            TopAppBar(
                title = {
                        Text(text = "Results")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_filters),
                            contentDescription = ""
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_sort),
                            contentDescription = ""
                        )
                    }
                }
            )
            SearchedCars(cars = uiState.searchedCars, onCarClick = onCarClick)
        }
    } else {
        LoadingIndicator()
    }
}

@Composable
fun SearchedCars(cars: List<Car>, onCarClick: (Int) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cars) { car ->
            CarItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                car = car,
                onCarClick = onCarClick
            )
        }
    }
}