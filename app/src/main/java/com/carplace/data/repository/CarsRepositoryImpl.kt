package com.carplace.data.repository

import android.net.Uri
import com.carplace.result.Result
import com.carplace.ui.viewmodel.Car
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : CarsRepository {

    private val carsFlow = MutableStateFlow<List<Car>>(emptyList())
    override fun getLastAddedCar(): Car = carsFlow.value.maxBy { it.id }

    override fun getCarById(id: Int): Car = carsFlow.value.first { it.id == id }

    override fun getCarsByFilters(
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
    ): List<Car> {
        return carsFlow.value.filter { car ->
            val matchesMake = makes.isEmpty() || makes.any { make -> make == car.carDetails.make }
            val matchesModel =
                models.isEmpty() || models.any { model -> model == car.carDetails.model }
            val matchesCategory =
                carCategories.isEmpty() || carCategories.any { category -> category == car.carDetails.category }
            val matchesMileage = (minMileage <= 0 || car.km >= minMileage) &&
                    (maxMileage <= 0 || car.km <= maxMileage)
            val matchesFirstRegistration =
                (minFirstRegistration <= 0 || car.carDetails.firstRegistration >= minFirstRegistration.toString()) &&
                        (maxFirstRegistration <= 0 || car.carDetails.firstRegistration <= maxFirstRegistration.toString())
            val matchesPrice = (minPrice <= 0 || car.price >= minPrice) &&
                    (maxPrice <= 0 || car.price <= maxPrice)
            val matchesLocation =
                carLocations.isEmpty() || carLocations.any { location -> location == car.location }
            val matchesFuelType =
                fuelTypes.isEmpty() || fuelTypes.any { fuelType -> fuelType == car.carDetails.fuelType }

            matchesMake && matchesModel && matchesCategory && matchesMileage &&
                    matchesFirstRegistration && matchesPrice && matchesLocation && matchesFuelType
        }
    }

    override suspend fun addCar(car: Car, photosUri: List<Uri>): Flow<Result<Unit>> {
        return flow {
            val uploadResults = mutableListOf<Uri>()

            coroutineScope {
                photosUri.forEachIndexed { index, uri ->
                    launch(Dispatchers.IO) {
                        val imageFileName = "${System.currentTimeMillis()}_$index.jpg"
                        val imageRef = storage.reference.child(imageFileName)
                        imageRef.putFile(uri).await()

                        val downloadUrl = imageRef.downloadUrl.await()
                        uploadResults.add(downloadUrl)
                    }
                }
            }
            withContext(Dispatchers.Main) {
                firestore.collection("cars").add(car.copy(images = uploadResults.map { it.toString() })).await()
            }

            emit(Result.Success(Unit))
        }.catch { error -> Result.Error(error) }
    }


    override suspend fun getCars(): Flow<Result<List<Car>>> {
        return flow {
            val result = firestore.collection("cars").get().await()
            val cars = result.documents.mapNotNull { it.toObject(Car::class.java) }
            carsFlow.value = cars
            emit(Result.Success(cars))
        }
    }

    override suspend fun deleteCar(id: Int): Flow<Result<Unit>> {
        return flow {
            val carQuery = firestore.collection("cars").whereEqualTo("id", id).get().await()
            carQuery.let {
                if (it.documents.isNotEmpty()) {
                    it.documents.forEach { document ->
                        firestore.collection("cars").document(document.id).delete().await()
                    }
                }
            }
            emit(Result.Success(Unit))
        }.catch { error -> Result.Error(error) }
    }
}