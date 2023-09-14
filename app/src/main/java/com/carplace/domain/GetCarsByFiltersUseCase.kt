package com.carplace.domain

import com.carplace.data.repository.CarsRepository
import com.carplace.result.Result
import com.carplace.ui.viewmodel.Car
import com.carplace.ui.viewmodel.ChosenFilters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCarsByFiltersUseCase @Inject constructor(
    private val repository: CarsRepository
) {
    operator fun invoke(
        filters: ChosenFilters
    ): List<Car> = repository.getCarsByFilters(
        filters.makes,
        filters.models,
        filters.categories,
        filters.minMileage,
        filters.maxMileage,
        filters.minFirstRegistration,
        filters.maxFirstRegistration,
        filters.minPrice,
        filters.maxPrice,
        filters.locations,
        filters.fuelTypes
    )
}
