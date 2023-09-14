package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.data.repository.AuthRepository
import com.carplace.data.repository.CarsRepository
import com.carplace.result.handle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListingsViewModel @Inject constructor(
    private val repository: CarsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(MyListingsUiState())
        private set

    init {
        loadData()
    }

   fun loadData() {
        uiState = uiState.copy(isRefreshing = true)
        val email = authRepository.getCurrentUser()?.email ?: ""
        viewModelScope.launch {
            repository.getCars().collectLatest { result ->
                result.handle { data ->
                    uiState = uiState.copy(
                        cars = data.data.filter { email == it.userEmail },
                        isRefreshing = false
                    )
                }
            }
        }
    }

    fun deleteCar(id: Int) {
        uiState = uiState.copy(isRefreshing = true)
        viewModelScope.launch {
            repository.deleteCar(id).collectLatest { result ->
                result.handle {
                    uiState = uiState.copy(
                        isRefreshing = false,
                        cars = uiState.cars.filter { it.id != id })
                }
            }
        }
    }
}

data class MyListingsUiState(
    val cars: List<Car> = emptyList(),
    val isRefreshing: Boolean = false
)