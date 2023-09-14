package com.carplace.data.repository

import android.net.Uri
import com.carplace.result.Result
import com.carplace.ui.viewmodel.Car
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    fun getLastAddedCar(): Car
    fun getCarById(id: Int): Car

    fun getCarsByFilters(
        makes: List<String>,
        models: List<String>,
        carCategories: List<String>,
        minMileage: Int,
        maxMileage: Int,
        minFirstRegistration: Int,
        maxFirstRegistration: Int,
        minPrice: Int,
        maxPrice: Int,
        carLocations: List<String>,
        fuelTypes: List<String>
    ): List<Car>

    suspend fun addCar(car: Car, photosUri: List<Uri>): Flow<Result<Unit>>

    suspend fun getCars(): Flow<Result<List<Car>>>

    suspend fun deleteCar(id: Int): Flow<Result<Unit>>
}