package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.domain.GetCarByIdUseCase
import com.carplace.result.handle
import com.carplace.ui.navigation.carIdArg
import com.carplace.ui.screens.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, getCarByIdUseCase: GetCarByIdUseCase
) : ViewModel() {


    private val carId = checkNotNull(savedStateHandle.get<Int>(carIdArg))

    var uiState by mutableStateOf(CarDetailsUiState())
        private set

    init {
        viewModelScope.launch {
            getCarByIdUseCase(carId).collectLatest { carResult ->
                    carResult.handle { car ->
                        uiState = uiState.copy(car = car.data, isInitComplete = true)
                    }
                }
        }
    }

}

data class CarDetailsUiState(
    val car: Car = Car(), val isInitComplete: Boolean = false
)