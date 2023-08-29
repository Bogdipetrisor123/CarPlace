package com.carplace.data.repository

import com.carplace.ui.screens.Car
import com.carplace.R
import com.carplace.ui.screens.CarDetails
import com.carplace.ui.screens.CarFeatures


fun recentAddedCars(): List<Car> = listOf(
    Car(
        imagesRes = listOf(
            R.drawable.auris1,
            R.drawable.auris2,
            R.drawable.auris3,
            R.drawable.auris4
        ),
        title = "Toyota Auris 1.6 Hybrid",
        price = 12200,
        km = 183020,
        id = 1,
        carDetails = CarDetails(
            make = "Toyota",
            model = "Auris",
            category = "Break",
            cubicCapacity = "1600 cm3",
            horsePower = "100 hp",
            fuelType = "Hybrid",
            numberOfSeats = "5",
            gearBox = "Automatic",
            firstRegistration = "2012",
            colour = "White",
            condition = "Used"
        ),
        carFeatures = CarFeatures(
            abs = "ABS",
            adaptiveCruiseControl = "Adaptive Cruise Control",
            ambientLighting = "Ambient Lighting",
            appleCarPlay = "Apple Car Play",
            adaptiveCorneringLights = "Adaptive Cornering Lights",
            alloyWheels = "Alloy Wheels",
            androidAuto = "Android Auto",
            armRest = "Arm rest",
            bluetooth = "Bluetooth",
            biXenonHeadLights = " Bi-xenon headlights"
        )
    ),
    Car(
        imagesRes = listOf(R.drawable.honda_type_r),
        title = "Honda Civic Type R",
        price = 35990,
        km = 12000,
        id = 2,
    ),
    Car(
        imagesRes = listOf(R.drawable.polo),
        title = "Volkswagen Polo",
        price = 6000,
        km = 80000,
        id = 3,
        carDetails = CarDetails(
            make = "Volkswagen",
            model = "Passat",
            category = "Sedan",
            cubicCapacity = "1600 cm3",
            horsePower = "100 hp",
            fuelType = "Diesel",
            numberOfSeats = "5",
            gearBox = "Manual",
            firstRegistration = "2010",
            colour = "Blue",
            condition = "Used"
        ),
        carFeatures = CarFeatures(
            abs = "ABS",
            adaptiveCruiseControl = "Adaptive Cruise Control",
            androidAuto = "Android Auto",
            armRest = "Arm rest",
            bluetooth = "Bluetooth",
            biXenonHeadLights = " Bi-xenon headlights"
        )
    ),
    Car(
        imagesRes = listOf(R.drawable.audirs6),
        title = "Audi RS6 Avant",
        price = 99000,
        km = 15000,
        id = 4
    ),
    Car(
        imagesRes = listOf(R.drawable.golfgti),
        title = "Volkswagen Golf GTI",
        price = 7000,
        km = 250000,
        id = 5,
        carDetails = CarDetails(
            make = "Volkswagen",
            model = "Golf",
            category = "Sedan",
            cubicCapacity = "2000 cm3",
            horsePower = "100 hp",
            fuelType = "Gasoline",
            numberOfSeats = "5",
            gearBox = "Automatic",
            firstRegistration = "2008",
            colour = "Black",
            condition = "Used"
        ),
        carFeatures = CarFeatures(
            abs = "ABS",
            adaptiveCruiseControl = "Adaptive Cruise Control",
            ambientLighting = "Ambient Lighting",
            appleCarPlay = "Apple Car Play",
            adaptiveCorneringLights = "Adaptive Cornering Lights",
            alloyWheels = "Alloy Wheels",
            androidAuto = "Android Auto",
            armRest = "Arm rest",
            bluetooth = "Bluetooth",
            biXenonHeadLights = " Bi-xenon headlights"
        )
    ),
    Car(
        imagesRes = listOf(R.drawable.mustang),
        title = "Ford Mustang GT",
        price = 55000,
        km = 35000,
        id = 6
    )
)

fun popularCars(): List<Car> = listOf(
    Car(
        imagesRes = listOf(R.drawable.tesla_s),
        title = "Tesla Model S",
        price = 89999,
        km = 5000,
        id = 7
    ),
    Car(
        imagesRes = listOf(R.drawable.eqe1, R.drawable.eqe2, R.drawable.eqe3, R.drawable.eqe4),
        title = "Mercedes-Benz EQE AMG 45",
        price = 127211,
        km = 6900,
        id = 8,
        carDetails = CarDetails(
            make = "Mercedes",
            model = "EQE",
            category = "Sedan",
            cubicCapacity = "3000 cm3",
            horsePower = "400 hp",
            fuelType = "Hybrid",
            numberOfSeats = "5",
            gearBox = "Automatic",
            firstRegistration = "2021",
            colour = "Black",
            condition = "New"
        ),
        carFeatures = CarFeatures(
            abs = "ABS",
            adaptiveCruiseControl = "Adaptive Cruise Control",
            ambientLighting = "Ambient Lighting",
            appleCarPlay = "Apple Car Play",
            adaptiveCorneringLights = "Adaptive Cornering Lights",
            alloyWheels = "Alloy Wheels",
            androidAuto = "Android Auto",
            armRest = "Arm rest",
            bluetooth = "Bluetooth",
            biXenonHeadLights = " Bi-xenon headlights"
        )
    ),
    Car(
        imagesRes = listOf(R.drawable.porsche911),
        title = "Porsche 911 Carrera",
        price = 109800,
        km = 8000,
        id = 9,
        carDetails = CarDetails(
            make = "Porsche",
            model = "911 Carrera",
            category = "Coupe",
            cubicCapacity = "4000 cm3",
            horsePower = "500 hp",
            fuelType = "Gasoline",
            numberOfSeats = "2",
            gearBox = "Automatic",
            firstRegistration = "2021",
            colour = "Blue",
            condition = "New"
        ),
        carFeatures = CarFeatures(
            abs = "ABS",
            adaptiveCruiseControl = "Adaptive Cruise Control",
            ambientLighting = "Ambient Lighting",
            appleCarPlay = "Apple Car Play",
            adaptiveCorneringLights = "Adaptive Cornering Lights",
            alloyWheels = "Alloy Wheels",
            androidAuto = "Android Auto",
            armRest = "Arm rest",
            bluetooth = "Bluetooth",
            biXenonHeadLights = " Bi-xenon headlights"
        )
    ),
    Car(
        imagesRes = listOf(R.drawable.volvoxc60),
        title = "Volvo XC60",
        price = 45000,
        km = 25000,
        id = 10
    ),
    Car(
        imagesRes = listOf(R.drawable.jaguarftype),
        title = "Jaguar F-Type",
        price = 85000,
        km = 15000,
        id = 11
    ),
    Car(
        imagesRes = listOf(R.drawable.q5),
        title = "Audi Q5",
        price = 42000,
        km = 18000,
        id = 12
    )
)


fun nearsCars(): List<Car> = listOf(
    Car(
        imagesRes = listOf(
            R.drawable.x6m501,
            R.drawable.x6m502,
            R.drawable.x6m503,
            R.drawable.x6m504,
            R.drawable.x6m505
        ),
        title = "BMW X6 M M50d",
        price = 78900,
        km = 68000,
        id = 13,
        carDetails = CarDetails(
            make = "BMW",
            model = "X6",
            category = "SUV",
            cubicCapacity = "4000 cm3",
            horsePower = "550 hp",
            fuelType = "Diesel",
            numberOfSeats = "5",
            gearBox = "Automatic",
            firstRegistration = "2021",
            colour = "Black",
            condition = "Used"
        ),
        carFeatures = CarFeatures(
            abs = "ABS",
            adaptiveCruiseControl = "Adaptive Cruise Control",
            ambientLighting = "Ambient Lighting",
            appleCarPlay = "Apple Car Play",
            adaptiveCorneringLights = "Adaptive Cornering Lights",
            alloyWheels = "Alloy Wheels",
            androidAuto = "Android Auto",
            armRest = "Arm rest",
            bluetooth = "Bluetooth",
            biXenonHeadLights = " Bi-xenon headlights"
        )
    ),
    Car(
        imagesRes = listOf(R.drawable.audia3),
        title = "Audi A3",
        price = 6500,
        km = 90000,
        id = 14
    ),
    Car(
        imagesRes = listOf(R.drawable.skodaoctavia),
        title = "Skoda Octavia",
        price = 5500,
        km = 70000,
        id = 15
    ),
    Car(
        imagesRes = listOf(R.drawable.fordfocus),
        title = "Ford Focus",
        price = 5000,
        km = 60000,
        id = 16
    ),
    Car(
        imagesRes = listOf(R.drawable.passatcc),
        title = "Volkswagen Passat CC",
        price = 7000,
        km = 100000,
        id = 17
    )
)