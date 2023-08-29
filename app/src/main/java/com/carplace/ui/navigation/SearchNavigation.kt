package com.carplace.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.carplace.ui.screens.SearchRoute
import com.carplace.ui.viewmodel.SearchViewModel

const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute,navOptions)
}

fun NavGraphBuilder.searchScreen(
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchViewModel
) {
    composable(route = searchRoute) {
        SearchRoute(onSearchClick = onSearchClick, viewModel = viewModel)
    }
}