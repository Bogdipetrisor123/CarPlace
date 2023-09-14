package com.carplace.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.carplace.ui.viewmodel.MyListingsUiState
import com.carplace.ui.viewmodel.MyListingsViewModel

@Composable
internal fun MyListingRoute(
    viewModel: MyListingsViewModel = hiltViewModel(),
    onCarClick: (Int) -> Unit
) {
    MyListingScreen(
        uiState = viewModel.uiState,
        onCarClick = onCarClick,
        onDeleteClick = viewModel::deleteCar,
        loadData = viewModel::loadData
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MyListingScreen(
    uiState: MyListingsUiState,
    onCarClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    loadData: () -> Unit
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = uiState.isRefreshing, onRefresh = loadData)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (!uiState.isRefreshing) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "My Listings")
                    },
                )
                SearchedCars(
                    cars = uiState.cars,
                    navigateFromMyListings = true,
                    onCarClick = onCarClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}