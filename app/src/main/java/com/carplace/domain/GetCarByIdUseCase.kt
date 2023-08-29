package com.carplace.domain

import com.carplace.data.repository.CarsRepository
import com.carplace.result.Result
import com.carplace.ui.screens.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCarByIdUseCase @Inject constructor(
   private val carsRepository: CarsRepository
) {
    operator fun invoke(id: Int): Flow<Result<Car>> {
        return flow {
            val car = carsRepository.getCarById(id)
            emit(Result.Success(car))
        }
            .catch { error -> Result.Error(error) }
    }
}