package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.domain.GetNearsCarsUseCase
import com.carplace.domain.GetPopularCarsUseCase
import com.carplace.domain.GetRecentAddedCarsUseCase
import com.carplace.result.handle
import com.carplace.ui.screens.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecentAddedCarsUseCase: GetRecentAddedCarsUseCase,
    private val getPopularCarsUseCase: GetPopularCarsUseCase,
    private val getNearsCarsUseCase: GetNearsCarsUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                getRecentAddedCarsUseCase(),
                getPopularCarsUseCase(),
                getNearsCarsUseCase()
            ) { recentAddedCars, popularCars, nearsCars ->
                recentAddedCars.handle { recentAddedCarsResult ->
                    popularCars.handle { popularCarsResult ->
                        nearsCars.handle { nearsCarsResult ->
                            uiState = uiState.copy(
                                isInitComplete = true,
                                recentAddedCars = recentAddedCarsResult.data,
                                popularCars = popularCarsResult.data,
                                nearsCars = nearsCarsResult.data
                            )
                        }
                    }
                }
            }.collect()
        }
    }
}

data class HomeUiState(
    val isInitComplete: Boolean = false,
    val recentAddedCars: List<Car> = emptyList(),
    val popularCars: List<Car> = emptyList(),
    val nearsCars: List<Car> = emptyList(),
)