package com.carplace.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplace.domain.GetCarsByFiltersUseCase
import com.carplace.result.handle
import com.carplace.ui.screens.Car
import com.carplace.ui.utils.getCategories
import com.carplace.ui.utils.getFuelTypes
import com.carplace.ui.utils.getLocations
import com.carplace.ui.utils.getMakes
import com.carplace.ui.utils.getModelsByMake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCarsByFiltersUseCase: GetCarsByFiltersUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SearchUiState())
        private set

    fun onSearchPressed() {
        viewModelScope.launch {
            getCarsByFiltersUseCase.invoke(uiState.chosenFilters).collectLatest { searchedCars ->
                searchedCars.handle { result ->
                    uiState = uiState.copy(isInitComplete = true, searchedCars = result.data)
                }
            }
        }
    }

    fun changeDialogState(isOpen: Boolean, selectedFilter: FilterCategory) {
        if (selectedFilter == FilterCategory.MODEL) {
            uiState = uiState.copy(models = getModelsByMake(uiState.makes.first { it.isSelected }))
        }
        uiState = uiState.copy(openDialog = isOpen, currentSelectedFilter = selectedFilter)
    }

    fun onSelectFilter(option: FilterOption) {
        //TODO : updateUiState
        when (uiState.currentSelectedFilter) {
            FilterCategory.MAKE -> uiState = uiState.copy(
                makes = updateOptionStatus(
                    option = option,
                    filterList = uiState.makes
                )
            )

            FilterCategory.MODEL -> uiState = uiState.copy(
                models = updateOptionStatus(
                    option = option,
                    filterList = uiState.models
                )
            )

            FilterCategory.CATEGORY -> uiState = uiState.copy(
                categories = updateOptionStatus(
                    option = option,
                    filterList = uiState.categories
                )
            )

            FilterCategory.FUEL_TYPE -> uiState = uiState.copy(
                fuelTypes = updateOptionStatus(
                    option = option,
                    filterList = uiState.fuelTypes
                )
            )

            FilterCategory.LOCATION -> uiState = uiState.copy(
                locations = updateOptionStatus(
                    option = option,
                    filterList = uiState.locations
                )
            )

            else -> {}
        }
    }

    fun dismissDialog(category: FilterCategory) {

        when (category) {
            FilterCategory.MAKE -> updateUiState(makes = uiState.makes.filter { it.isSelected }.map { it.option })
            FilterCategory.MODEL -> updateUiState(models = uiState.models.filter { it.isSelected }.map { it.option })
            FilterCategory.CATEGORY -> updateUiState(categories = uiState.categories.filter { it.isSelected }.map { it.option })
            FilterCategory.LOCATION -> updateUiState(locations = uiState.locations.filter { it.isSelected }.map { it.option })
            FilterCategory.FUEL_TYPE -> updateUiState(fuelTypes = uiState.fuelTypes.filter { it.isSelected }.map { it.option })
            else -> uiState = uiState.copy(openDialog = false)
        }
    }

    private fun updateUiState(
        makes: List<String> = uiState.chosenFilters.makes,
        models: List<String> = uiState.chosenFilters.models,
        categories: List<String> = uiState.chosenFilters.categories,
        fuelTypes: List<String> = uiState.chosenFilters.fuelTypes,
        locations: List<String> = uiState.chosenFilters.locations,
    ) {
        val chosenFilters = uiState.chosenFilters.copy(
            makes = makes,
            models = models,
            categories = categories,
            fuelTypes = fuelTypes,
            locations = locations
        )
        uiState = uiState.copy(openDialog = false, chosenFilters = chosenFilters)
    }

    private fun updateOptionStatus(
        option: FilterOption,
        filterList: List<FilterOption>
    ): List<FilterOption> {
        return filterList.map { filter ->
            if (filter.option == option.option) {
                filter.copy(isSelected = !filter.isSelected)
            } else {
                filter
            }
        }
    }

    private fun setPrice(onlyMinPrice: Boolean, price: Int) {
        uiState = if (onlyMinPrice) {
            uiState.copy(
                chosenFilters = uiState.chosenFilters.copy(minPrice = price)
            )
        } else {
            uiState.copy(
                chosenFilters = uiState.chosenFilters.copy(maxPrice = price)
            )
        }
    }

    private fun setFirstRegistration(onlyMinFirstRegistration: Boolean, year: Int) {
        uiState = if (onlyMinFirstRegistration) {
            uiState.copy(
                chosenFilters = uiState.chosenFilters.copy(minFirstRegistration = year)
            )
        } else {
            uiState.copy(
                chosenFilters = uiState.chosenFilters.copy(maxFirstRegistration = year)
            )
        }
    }

    private fun setMileage(onlyMinMileage: Boolean, mileage: Int) {
        uiState = if (onlyMinMileage) {
            uiState.copy(
                chosenFilters = uiState.chosenFilters.copy(minMileage = mileage)
            )
        } else {
            uiState.copy(
                chosenFilters = uiState.chosenFilters.copy(maxMileage = mileage)
            )
        }
    }

    fun onFilterValueChange(onlyMinValue: Boolean, value: Int, filterCategory: FilterCategory) {
        when (filterCategory) {
            FilterCategory.MILEAGE -> setMileage(onlyMinValue, value)
            FilterCategory.PRICE -> setPrice(onlyMinValue, value)
            FilterCategory.FIRST_REGISTRATION -> setFirstRegistration(onlyMinValue, value)
            else -> {}
        }
    }
}

data class SearchUiState(
    val chosenFilters: ChosenFilters = ChosenFilters(),
    val searchedCars: List<Car> = emptyList(),
    val makes: List<FilterOption> = getMakes(),
    val models: List<FilterOption> = emptyList(),
    val categories: List<FilterOption> = getCategories(),
    val fuelTypes: List<FilterOption> = getFuelTypes(),
    val locations: List<FilterOption> = getLocations(),
    val isInitComplete: Boolean = false,
    val openDialog: Boolean = false,
    val currentSelectedFilter: FilterCategory = FilterCategory.MAKE
)

data class FilterOption(
    val option: String = "",
    val isSelected: Boolean = false
)

data class ChosenFilters(
    val makes: List<String> = emptyList(),
    val models: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val minMileage: Int = 0,
    val maxMileage: Int = 0,
    val minFirstRegistration: Int = 0,
    val maxFirstRegistration: Int = 0,
    val minPrice: Int = 0,
    val maxPrice: Int = 0,
    val locations: List<String> = emptyList(),
    val fuelTypes: List<String> = emptyList(),
)

enum class FilterCategory(private val filterName: String) {
    MAKE("Make"),
    MODEL("Model"),
    CATEGORY("Category"),
    MILEAGE("Mileage"),
    FIRST_REGISTRATION("First Registration"),
    PRICE("Price"),
    LOCATION("Location"),
    FUEL_TYPE("Fuel type");

    fun getFilterName() = filterName
}

