package com.carplace.domain

import com.carplace.data.repository.CarsRepository
import com.carplace.result.Result
import com.carplace.ui.viewmodel.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPopularCarsUseCase @Inject constructor(private val carsRepository: CarsRepository) {
//    operator fun invoke(): Flow<Result<List<Car>>> {
//        return flow {
//            val cars = carsRepository.getPopularCars()
//            emit(Result.Success(cars))
//        }
//            .catch { error -> Result.Error(error) }
//    }
}