package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.carplace.ui.screens.SellCarRoute

const val sellCarRoute = "sell_car_route"

fun NavController.navigateToSellCar(navOptions: NavOptions? = null) {
    this.navigate(sellCarRoute,navOptions)
}

fun NavGraphBuilder.sellCarScreen() {
    composable(route = sellCarRoute) {
        SellCarRoute()
    }
}