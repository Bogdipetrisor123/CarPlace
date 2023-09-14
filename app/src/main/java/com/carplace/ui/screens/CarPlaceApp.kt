package com.carplace.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.carplace.ui.CarPlaceAppState
import com.carplace.ui.navigation.CarPlaceNavHost
import com.carplace.ui.navigation.TopLevelDestination
import com.carplace.ui.navigation.navigateToAuthentication
import com.carplace.ui.rememberCarPlaceAppState
import com.carplace.ui.viewmodel.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarPlaceApp(
    appState: CarPlaceAppState = rememberCarPlaceAppState(),
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                CarPlaceBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    hasSession = viewModel.getCurrentUser() != null
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val destination = appState.currentTopLevelDestination
            if (destination != null && !appState.isAuthenticationRoute) {
                CarPlaceTopAppBar(titleRes = destination.titleTextId, doSignOut = {
                    viewModel.signOut()
                    appState.navController.navigateToAuthentication()
                })
            }
            CarPlaceNavHost(appState = appState, hasSession = viewModel.getCurrentUser() != null)
        }
    }
}

@Composable
private fun CarPlaceBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    hasSession: Boolean
) {

    NavigationBar {
        destinations.forEach { destination ->
            val shouldShowDestination =
                if (destination == TopLevelDestination.SELL || destination == TopLevelDestination.MY_LISTINGS) {
                    hasSession
                } else {
                    true
                }
            if (shouldShowDestination) {
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = destination.iconDrawable),
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = destination.titleTextId)) })
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false