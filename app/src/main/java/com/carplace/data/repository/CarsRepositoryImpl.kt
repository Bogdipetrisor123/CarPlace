package com.carplace.data.repository

import com.carplace.result.Result
import com.carplace.ui.screens.Car
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val CARS_COLLECTION = "cars"

class CarsRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : CarsRepository {

    override suspend fun getRecentAddedCars(): List<Car> = recentAddedCars()

    override suspend fun getPopularCars(): List<Car> = popularCars()

    override suspend fun getNearsCars(): List<Car> = nearsCars()

    override suspend fun getCarById(id: Int): Car {
        val cars = recentAddedCars().plus(popularCars()).plus(nearsCars())
        return cars.first { it.id == id }
    }

    override suspend fun addCar(car: Car): Flow<Result<DocumentReference>> {
        return flow {
            val result = db.collection("cars").add(car).await()
            emit(Result.Success(result))
        }.catch { error -> Result.Error(error) }
    }
}