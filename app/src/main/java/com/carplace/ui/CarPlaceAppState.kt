package com.carplace.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.carplace.data.repository.AuthRepository
import com.carplace.ui.navigation.TopLevelDestination
import com.carplace.ui.navigation.authRoute
import com.carplace.ui.navigation.carDetailsRoute
import com.carplace.ui.navigation.carIdArg
import com.carplace.ui.navigation.homeRoute
import com.carplace.ui.navigation.navigateToHome
import com.carplace.ui.navigation.navigateToSearch
import com.carplace.ui.navigation.navigateToSellCar
import com.carplace.ui.navigation.searchRoute
import com.carplace.ui.navigation.searchedCarsRoute

@Composable
fun rememberCarPlaceAppState(
    navController: NavHostController = rememberNavController(),
): CarPlaceAppState {
    return remember(navController) {
        CarPlaceAppState(navController)
    }
}

@Stable
class CarPlaceAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when(currentDestination?.route) {
            homeRoute -> TopLevelDestination.HOME
            searchRoute -> TopLevelDestination.Search
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = when(currentDestination?.route) {
            "$carDetailsRoute/{$carIdArg}" -> false
            searchedCarsRoute -> false
            authRoute -> false
            else -> true
        }

    val isAuthenticationRoute: Boolean
        @Composable get() = currentDestination?.route == authRoute

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.Search -> navController.navigateToSearch(topLevelNavOptions)
            TopLevelDestination.Sell -> navController.navigateToSellCar(topLevelNavOptions)
//            TopLevelDestination.Favorites -> TODO()
        }
    }
}