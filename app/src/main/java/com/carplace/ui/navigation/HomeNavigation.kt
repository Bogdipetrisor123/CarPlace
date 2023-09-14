package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.carplace.ui.screens.HomeRoute

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onCarClick: (Int) -> Unit,
    onSearchBarClick: () -> Unit
) {
    // TODO: Handle back stack for each top-level destination. At the moment each top-level
    // destination may have own search screen's back stack.
    composable(route = homeRoute) {
        HomeRoute(
            onCarClick = onCarClick
        )
    }
}