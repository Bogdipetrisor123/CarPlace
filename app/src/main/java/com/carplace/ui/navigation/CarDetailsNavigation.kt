package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carplace.ui.screens.CarDetailsRoute

internal const val carIdArg = "carId"
const val carDetailsRoute = "car_details_route"

fun NavController.navigateToCarDetails(carId: Int) {
    this.navigate("$carDetailsRoute/$carId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.carDetailsScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$carDetailsRoute/{$carIdArg}",
        arguments = listOf(
            navArgument(carIdArg) { type = NavType.IntType }
        )
    ) {
        CarDetailsRoute(onBackClick)
    }
}