package com.carplace.data.repository

import com.carplace.ui.screens.Car

interface SearchRepository {

    suspend fun getCarsByFilters(
        make: List<String>,
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
}