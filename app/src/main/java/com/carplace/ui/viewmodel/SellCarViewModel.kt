package com.carplace.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.data.repository.AuthRepository
import com.carplace.data.repository.CarsRepository
import com.carplace.result.Result
import com.carplace.ui.enums.Type
import com.carplace.ui.screens.CarDetails
import com.carplace.ui.utils.getCategories
import com.carplace.ui.utils.getColors
import com.carplace.ui.utils.getConditions
import com.carplace.ui.utils.getFeatures
import com.carplace.ui.utils.getFuelTypes
import com.carplace.ui.utils.getGearboxTypes
import com.carplace.ui.utils.getLocations
import com.carplace.ui.utils.getMakes
import com.carplace.ui.utils.getModelsByMake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellCarViewModel @Inject constructor(
    private val repository: CarsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf(SellCarUiState())
        private set

    init {
        uiState = uiState.copy(
            makes = getMakes(),
            models = emptyList(),
            categories = getCategories(),
            fuelTypes = getFuelTypes(),
            gearBoxTypes = getGearboxTypes(),
            colors = getColors(),
            condition = getConditions(),
            locations = getLocations(),
            features = getFeatures()
        )
    }

    private fun updateUiState(
        title: String = uiState.title,
        phoneNumber: String = uiState.phoneNumber,
        photosUri: List<Uri> = uiState.photosUri,
        makes: List<FilterOption> = uiState.makes,
        models: List<FilterOption> = uiState.models,
        categories: List<FilterOption> = uiState.categories,
        mileage: Int = uiState.mileage,
        price: Int = uiState.price,
        locations: List<FilterOption> = uiState.locations,
        cubicCapacity: Int = uiState.cubicCapacity,
        horsePower: Int = uiState.horsePower,
        fuelTypes: List<FilterOption> = uiState.fuelTypes,
        seatsNumber: Int = uiState.seatsNumber,
        gearBoxTypes: List<FilterOption> = uiState.gearBoxTypes,
        firstRegistration: Int = uiState.firstRegistration,
        colors: List<FilterOption> = uiState.colors,
        condition: List<FilterOption> = uiState.condition,
        features: List<FilterOption> = uiState.features,
        isLoading: Boolean = uiState.isLoading
    ) {
        val buttonEnabled = isButtonEnabled(
            makes,
            models,
            categories,
            mileage,
            price,
            locations,
            cubicCapacity,
            horsePower,
            fuelTypes,
            seatsNumber,
            gearBoxTypes,
            firstRegistration,
            colors,
            condition,
            features
        )

        uiState = uiState.copy(
            title = title,
            photosUri = photosUri,
            phoneNumber = phoneNumber,
            makes = makes,
            models = models,
            categories = categories,
            mileage = mileage,
            price = price,
            locations = locations,
            cubicCapacity = cubicCapacity,
            horsePower = horsePower,
            fuelTypes = fuelTypes,
            seatsNumber = seatsNumber,
            gearBoxTypes = gearBoxTypes,
            firstRegistration = firstRegistration,
            colors = colors,
            condition = condition,
            features = features,
            buttonEnabled = buttonEnabled,
            isLoading = isLoading
        )
    }


    fun onOptionSelected(
        type: Type,
        option: FilterOption
    ) {
        when (type) {
            Type.CATEGORY -> updateUiState(categories = uiState.categories.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.COLOR -> updateUiState(colors = uiState.colors.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.MAKE -> {
                updateUiState(makes = uiState.makes.map {
                    if (it == option) {
                        it.copy(isSelected = !it.isSelected)
                    } else it
                })
                val make = uiState.makes.firstOrNull { it.isSelected }
                if (make != null) {
                    updateUiState(models = getModelsByMake(make))
                }
            }

            Type.MODELS -> updateUiState(models = uiState.models.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.FUEL -> updateUiState(fuelTypes = uiState.fuelTypes.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.GEAR_BOX -> updateUiState(gearBoxTypes = uiState.gearBoxTypes.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.CONDITION -> updateUiState(condition = uiState.condition.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.LOCATION -> updateUiState(locations = uiState.locations.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            Type.FEATURES -> updateUiState(features = uiState.features.map {
                if (it == option) {
                    it.copy(isSelected = !it.isSelected)
                } else it
            })

            else -> uiState
        }
    }

    fun onValueChange(type: Type, value: Int) {
        when (type) {
            Type.MILEAGE -> updateUiState(mileage = value)
            Type.PRICE -> updateUiState(price = value)
            Type.CUBIC_CAPACITY -> updateUiState(cubicCapacity = value)
            Type.HP -> updateUiState(horsePower = value)
            Type.SEATS -> updateUiState(seatsNumber = value)
            Type.FIRST_REGISTRATION -> updateUiState(firstRegistration = value)
            else -> uiState
        }
    }

    fun setPhotosUri(photosUri: List<Uri>) = updateUiState(photosUri = photosUri)

    fun setPhoneNumber(number: String) = updateUiState(phoneNumber = number)

    fun setCarTitle(title: String) = updateUiState(title = title)

    fun onAddCarClicked() {
        updateUiState(isLoading = true)
        viewModelScope.launch {
            repository.addCar(
                Car(
                    id = repository.getLastAddedCar().id + 1,
                    userEmail = authRepository.getCurrentUser()?.email ?: "",
                    phoneNumber = uiState.phoneNumber,
                    title = uiState.title,
                    price = uiState.price,
                    km = uiState.mileage,
                    location = uiState.locations.first { it.isSelected }.option,
                    carDetails = CarDetails(
                        make = uiState.makes.first { it.isSelected }.option,
                        model = uiState.models.first { it.isSelected }.option,
                        category = uiState.categories.first { it.isSelected }.option,
                        cubicCapacity = uiState.cubicCapacity.toString(),
                        horsePower = uiState.horsePower.toString(),
                        fuelType = uiState.fuelTypes.first { it.isSelected }.option,
                        numberOfSeats = uiState.seatsNumber.toString(),
                        gearBox = uiState.gearBoxTypes.first { it.isSelected }.option,
                        firstRegistration = uiState.firstRegistration.toString(),
                        colour = uiState.colors.first { it.isSelected }.option,
                        condition = uiState.condition.first { it.isSelected }.option
                    ),
                    carFeatures = uiState.features.filter { it.isSelected }
                ),
                photosUri = uiState.photosUri
            ).collectLatest { result ->
                uiState = when (result) {
                    is Result.Success -> uiState.copy(isLoading = false)
                    is Result.Error -> uiState.copy(isLoading = false)
                }
            }
        }
    }

    private fun isButtonEnabled(
        makes: List<FilterOption> = uiState.makes,
        models: List<FilterOption> = uiState.models,
        categories: List<FilterOption> = uiState.categories,
        mileage: Int = uiState.mileage,
        price: Int = uiState.price,
        locations: List<FilterOption> = uiState.locations,
        cubicCapacity: Int = uiState.cubicCapacity,
        horsePower: Int = uiState.horsePower,
        fuelTypes: List<FilterOption> = uiState.fuelTypes,
        seatsNumber: Int = uiState.seatsNumber,
        gearBoxTypes: List<FilterOption> = uiState.gearBoxTypes,
        firstRegistration: Int = uiState.firstRegistration,
        colors: List<FilterOption> = uiState.colors,
        condition: List<FilterOption> = uiState.condition,
        features: List<FilterOption> = uiState.features,
        phoneNumber: String = uiState.phoneNumber,
        photosUri: List<Uri> = uiState.photosUri,
        title: String = uiState.title
    ): Boolean {
        val nonEmptyLists = listOf(
            makes,
            models,
            categories,
            locations,
            fuelTypes,
            gearBoxTypes,
            colors,
            condition,
            features
        ).all { it.any { it.isSelected } }

        val numberInputFields = listOf(
            mileage,
            price,
            cubicCapacity,
            horsePower,
            seatsNumber,
            firstRegistration
        ).all { it > 0 }
        return nonEmptyLists && numberInputFields && phoneNumber.isNotEmpty() && photosUri.isNotEmpty() && title.isNotEmpty()
    }
}

data class SellCarUiState(
    val title: String = "",
    val phoneNumber: String = "",
    val photosUri: List<Uri> = emptyList(),
    val makes: List<FilterOption> = emptyList(),
    val models: List<FilterOption> = emptyList(),
    val categories: List<FilterOption> = emptyList(),
    val mileage: Int = 0,
    val price: Int = 0,
    val locations: List<FilterOption> = emptyList(),
    val cubicCapacity: Int = 0,
    val horsePower: Int = 0,
    val fuelTypes: List<FilterOption> = emptyList(),
    val seatsNumber: Int = 0,
    val gearBoxTypes: List<FilterOption> = emptyList(),
    val firstRegistration: Int = 0,
    val colors: List<FilterOption> = emptyList(),
    val condition: List<FilterOption> = emptyList(),
    val features: List<FilterOption> = emptyList(),
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false
)

data class Car(
    val id: Int = 0,
    val userEmail: String = "",
    val phoneNumber: String = "",
    val title: String = "",
    val price: Int = 0,
    val km: Int = 0,
    val location: String = "",
    val images: List<String> = emptyList(),
    val carDetails: CarDetails = CarDetails(),
    val carFeatures: List<FilterOption> = emptyList()
)