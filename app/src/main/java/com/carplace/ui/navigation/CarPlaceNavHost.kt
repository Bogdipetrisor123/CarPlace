package com.carplace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.carplace.ui.CarPlaceAppState
import com.carplace.ui.viewmodel.SearchViewModel

@Composable
fun CarPlaceNavHost(
    appState: CarPlaceAppState,
    modifier: Modifier = Modifier,
    hasSession: Boolean,
) {
    val navController = appState.navController
    val searchViewModel: SearchViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = if (hasSession) {
            homeRoute
        } else {
            authRoute
        },
        modifier = modifier,
    ) {
        authenticationScreen(goToHomeScreen = { navController.navigateToHome(null) })
        homeScreen(
            onCarClick = { carId -> navController.navigateToCarDetails(carId) },
            onSearchBarClick = { appState.navigateToTopLevelDestination(TopLevelDestination.SEARCH) }
        )
        searchScreen(
            onSearchClick = { navController.navigateToSearchedCars() },
            onBackClick = {},
            viewModel = searchViewModel
        )
        sellCarScreen()
        carDetailsScreen(onBackClick = navController::popBackStack)
        searchedCarsScreen(
            onBackClick = navController::popBackStack,
            onCarClick = { carId -> navController.navigateToCarDetails(carId) },
            viewModel = searchViewModel
        )
        myListingsScreen(
            onCarClick = { carId -> navController.navigateToCarDetails(carId) })
    }
}
