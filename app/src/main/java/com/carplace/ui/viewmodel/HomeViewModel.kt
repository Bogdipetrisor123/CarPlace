package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.data.repository.CarsRepository
import com.carplace.result.handle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadData()
    }

    private val popularCars = listOf("BMW", "Mercedes", "Audi", "Porsche")

    fun loadData() {
        uiState = uiState.copy(isRefreshing = true)
        viewModelScope.launch {
            carsRepository.getCars().collectLatest { result ->
                result.handle { data ->
                    uiState = uiState.copy(
                        isInitComplete = true,
                        isRefreshing = false,
                        recentAddedCars = data.data.sortedByDescending { it.id },
                        popularCars = data.data.filter { it.carDetails.make in popularCars },
                        nearsCars = data.data.filter { it.location == "Cluj"}
                    )
                }
            }
        }
    }
}

data class HomeUiState(
    val isInitComplete: Boolean = false,
    val isRefreshing: Boolean = false,
    val recentAddedCars: List<Car> = emptyList(),
    val popularCars: List<Car> = emptyList(),
    val nearsCars: List<Car> = emptyList(),
)