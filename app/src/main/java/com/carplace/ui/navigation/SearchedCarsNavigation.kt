package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.carplace.ui.screens.SearchedCarsRoute
import com.carplace.ui.viewmodel.SearchViewModel

const val searchedCarsRoute = "searched_cars_route"

fun NavController.navigateToSearchedCars(navOptions: NavOptions? = null) {
    this.navigate(searchedCarsRoute, navOptions)
}

fun NavGraphBuilder.searchedCarsScreen(
    onBackClick: () -> Unit,
    onCarClick: (Int) -> Unit,
    viewModel: SearchViewModel,
) {
    composable(route = searchedCarsRoute) {
        SearchedCarsRoute(
            onBackClick = onBackClick,
            onCarClick = { onCarClick(it) },
            viewModel = viewModel
        )
    }
}