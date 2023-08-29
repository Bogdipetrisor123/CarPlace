package com.carplace.data.repository

import com.carplace.R
import com.carplace.ui.screens.Car
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {
    override suspend fun getCarsByFilters(
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
        val cars = recentAddedCars().plus(popularCars()).plus(nearsCars())

        return cars.filter { car ->
            val matchesMake = makes.isEmpty() || makes.any { make -> make == car.carDetails.make }
            val matchesModel = models.isEmpty() || models.any { model -> model == car.carDetails.model }
            val matchesCategory = carCategories.isEmpty() || carCategories.any { category -> category == car.carDetails.category }
            val matchesMileage = (minMileage <= 0 || car.km >= minMileage) &&
                    (maxMileage <= 0 || car.km <= maxMileage)
            val matchesFirstRegistration = (minFirstRegistration <= 0 || car.carDetails.firstRegistration >= minFirstRegistration.toString()) &&
                    (maxFirstRegistration <= 0 || car.carDetails.firstRegistration <= maxFirstRegistration.toString())
            val matchesPrice = (minPrice <= 0 || car.price >= minPrice) &&
                    (maxPrice <= 0 || car.price <= maxPrice)
            val matchesLocation = carLocations.isEmpty() || carLocations.any { location -> location == car.location }
            val matchesFuelType = fuelTypes.isEmpty() || fuelTypes.any { fuelType -> fuelType == car.carDetails.fuelType }

            matchesMake && matchesModel && matchesCategory && matchesMileage &&
                    matchesFirstRegistration && matchesPrice && matchesLocation && matchesFuelType
        }
    }

}