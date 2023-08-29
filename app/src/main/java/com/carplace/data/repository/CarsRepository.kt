package com.carplace.data.repository

import com.carplace.result.Result
import com.carplace.ui.screens.Car
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    suspend fun getRecentAddedCars(): List<Car>

    suspend fun getPopularCars(): List<Car>

    suspend fun getNearsCars(): List<Car>

    suspend fun getCarById(id: Int): Car

    suspend fun addCar(car: Car): Flow<Result<DocumentReference>>
}