package com.carplace.domain

import com.carplace.data.repository.SearchRepository
import com.carplace.result.Result
import com.carplace.ui.screens.Car
import com.carplace.ui.viewmodel.ChosenFilters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCarsByFiltersUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(
        filters: ChosenFilters
    ): Flow<Result<List<Car>>> {
        return flow {
            val cars = searchRepository.getCarsByFilters(
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
            emit(Result.Success(cars))
        }
            .catch { error -> Result.Error(error) }
    }
}