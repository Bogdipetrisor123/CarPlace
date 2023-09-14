package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.carplace.ui.screens.MyListingRoute

const val myListingsRoute = "my_listings_route"

fun NavController.navigateToMyListings(navOptions: NavOptions? = null) {
    this.navigate(myListingsRoute, navOptions)
}

fun NavGraphBuilder.myListingsScreen(
    onCarClick: (Int) -> Unit
) {
    composable(route = myListingsRoute) {
        MyListingRoute(onCarClick = onCarClick)
    }

}