package com.carplace.ui.utils

import com.carplace.ui.enums.CarCategory
import com.carplace.ui.enums.CarColor
import com.carplace.ui.enums.CarCondition
import com.carplace.ui.enums.CarFeatures
import com.carplace.ui.enums.CarFuelType
import com.carplace.ui.enums.CarGearboxType
import com.carplace.ui.enums.CarLocation
import com.carplace.ui.enums.CarMake
import com.carplace.ui.viewmodel.FilterOption

fun getMakes() = CarMake.values().map { carMake -> carMake.make }

fun getModelsByMake(make: FilterOption): List<FilterOption> {
    return CarMake.values().first { it.make.option == make.option }.models
}

fun getCategories() = CarCategory.values().map { it.category }
fun getFuelTypes() = CarFuelType.values().map { it.fuelType }
fun getLocations() = CarLocation.values().map { it.location }
fun getGearboxTypes() = CarGearboxType.values().map { it.gearboxType }
fun getColors() = CarColor.values().map { it.color }
fun getConditions() = CarCondition.values().map { it.condition }
fun getFeatures() = CarFeatures.values().map { it.feature }



