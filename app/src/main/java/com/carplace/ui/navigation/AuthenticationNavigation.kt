package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.carplace.ui.screens.AuthScreen

const val authRoute = "authentication_route"

fun NavController.navigateToAuthentication() {
    this.navigate(authRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.authenticationScreen(
    goToHomeScreen: () -> Unit
) {
    composable(
        route = authRoute
    ) {
        AuthScreen(goToHomeScreen = goToHomeScreen)
    }
}