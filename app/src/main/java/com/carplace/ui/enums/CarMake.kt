package com.carplace.ui.enums

import com.carplace.ui.viewmodel.FilterOption

enum class CarMake(val make: FilterOption, val models: List<FilterOption>) {
    ALFA_ROMEO(
        FilterOption("Alfa Romeo"),
        listOf(
            FilterOption("4C"),
            FilterOption("Giulia"),
            FilterOption("Stelvio"),
            FilterOption("Giulietta")
        )
    ),
    AUDI(
        FilterOption("Audi"),
        listOf(
            FilterOption("A3"),
            FilterOption("A4"),
            FilterOption("A5"),
            FilterOption("A6"),
            FilterOption("A7"),
            FilterOption("Q3"),
            FilterOption("Q5"),
            FilterOption("Q7"),
            FilterOption("Q8")
        )
    ),
    ASTON_MARTIN(
        FilterOption("Aston Martin"),
        listOf(
            FilterOption("DB11"),
            FilterOption("Vantage"),
            FilterOption("DBS Superleggera"),
            FilterOption("DBX")
        )
    ),
    BMW(
        FilterOption("BMW"),
        listOf(
            FilterOption("3 Series"),
            FilterOption("4 series"),
            FilterOption("5 Series"),
            FilterOption("7 Series"),
            FilterOption("X3"),
            FilterOption("X4"),
            FilterOption("X5"),
            FilterOption("X6"),
            FilterOption("X7")
        )
    ),
    CHEVROLET(
        FilterOption("Chevrolet"),
        listOf(
            FilterOption("Camaro"),
            FilterOption("Corvette"),
            FilterOption("Malibu"),
            FilterOption("Traverse"),
            FilterOption("Tahoe"),
            FilterOption("Suburban")
        )
    ),
    DACIA(
        FilterOption("Dacia"),
        listOf(
            FilterOption("Sandero"),
            FilterOption("Duster"),
            FilterOption("Logan"),
            FilterOption("Lodgy"),
            FilterOption("Dokker")
        )
    ),
    FIAT(
        FilterOption("Fiat"),
        listOf(
            FilterOption("500"),
            FilterOption("Panda"),
            FilterOption("Tipo"),
            FilterOption("500X"),
            FilterOption("Doblo"),
            FilterOption("124 Spider")
        )
    ),
    FORD(
        FilterOption("Ford"),
        listOf(
            FilterOption("Mustang"),
            FilterOption("Focus"),
            FilterOption("Fiesta"),
            FilterOption("Mondeo"),
            FilterOption("Escape"),
            FilterOption("Explorer")
        )
    ),
    HONDA(
        FilterOption("Honda"),
        listOf(
            FilterOption("Civic"),
            FilterOption("Accord"),
            FilterOption("CR-V"),
            FilterOption("HR-V"),
            FilterOption("Pilot"),
            FilterOption("Odyssey")
        )
    ),
    HYUNDAI(
        FilterOption("Hyundai"),
        listOf(
            FilterOption("Elantra"),
            FilterOption("Sonata"),
            FilterOption("Tucson"),
            FilterOption("Santa Fe"),
            FilterOption("Kona"),
            FilterOption("Veloster")
        )
    ),
    LAND_ROVER(
        FilterOption("Land Rover"),
        listOf(
            FilterOption("Range Rover"),
            FilterOption("Discovery"),
            FilterOption("Defender"),
            FilterOption("Evoque"),
            FilterOption("Velar"),
            FilterOption("Discovery Sport")
        )
    ),
    MAZDA(
        FilterOption("Mazda"),
        listOf(
            FilterOption("Mazda3"),
            FilterOption("Mazda6"),
            FilterOption("CX-3"),
            FilterOption("CX-5"),
            FilterOption("CX-9"),
            FilterOption("MX-5 Miata")
        )
    ),
    MERCEDES(
        FilterOption("Mercedes"),
        listOf(
            FilterOption("C-Class"),
            FilterOption("E-Class"),
            FilterOption("S-Class"),
            FilterOption("GLC"),
            FilterOption("GLE"),
            FilterOption("GLA"),
            FilterOption("A-Class")
        )
    ),
    NISSAN(
        FilterOption("Nissan"),
        listOf(
            FilterOption("Altima"),
            FilterOption("Maxima"),
            FilterOption("Sentra"),
            FilterOption("Rogue"),
            FilterOption("Murano"),
            FilterOption("Pathfinder")
        )
    ),
    OPEL(
        FilterOption("Opel"),
        listOf(
            FilterOption("Corsa"),
            FilterOption("Astra"),
            FilterOption("Insignia"),
            FilterOption("Mokka"),
            FilterOption("Grandland X"),
            FilterOption("Crossland X")
        )
    ),
    PEUGEOT(
        FilterOption("Peugeot"),
        listOf(
            FilterOption("208"),
            FilterOption("308"),
            FilterOption("3008"),
            FilterOption("5008"),
            FilterOption("508"),
            FilterOption("2008")
        )
    ),
    PORSCHE(
        FilterOption("Porsche"),
        listOf(
            FilterOption("911"),
            FilterOption("Cayenne"),
            FilterOption("Panamera"),
            FilterOption("Macan"),
            FilterOption("Taycan")
        )
    ),
    RENAULT(
        FilterOption("Renault"),
        listOf(
            FilterOption("Clio"),
            FilterOption("Megane"),
            FilterOption("Captur"),
            FilterOption("Kadjar"),
            FilterOption("Talisman"),
            FilterOption("Koleos")
        )
    ),
    SEAT(
        FilterOption("Seat"),
        listOf(
            FilterOption("Leon"),
            FilterOption("Ibiza"),
            FilterOption("Ateca"),
            FilterOption("Arona"),
            FilterOption("Tarraco")
        )
    ),
    SKODA(
        FilterOption("Skoda"),
        listOf(
            FilterOption("Octavia"),
            FilterOption("Superb"),
            FilterOption("Kodiaq"),
            FilterOption("Karoq"),
            FilterOption("Scala")
        )
    ),
    TOYOTA(
        FilterOption("Toyota"),
        listOf(
            FilterOption("Corolla"),
            FilterOption("Camry"),
            FilterOption("Rav4"),
            FilterOption("Highlander"),
            FilterOption("Prius"),
            FilterOption("C-HR")
        )
    ),
    VOLKSWAGEN(
        FilterOption("Volkswagen"),
        listOf(
            FilterOption("Golf"),
            FilterOption("Passat"),
            FilterOption("Tiguan"),
            FilterOption("Atlas"),
            FilterOption("Touareg"),
            FilterOption("Arteon")
        )
    ),
    VOLVO(
        FilterOption("Volvo"),
        listOf(
            FilterOption("S60"),
            FilterOption("S90"),
            FilterOption("XC40"),
            FilterOption("XC60"),
            FilterOption("XC90"),
            FilterOption("V60")
        )
    );
}

enum class CarCategory(val category: FilterOption) {
    SEDAN(FilterOption("Sedan")),
    CABRIOLET(FilterOption("Cabriolet")),
    BREAK(FilterOption("Break")),
    COUPE(FilterOption("Coupe")),
    SUV(FilterOption("SUV")),
    SMALL_CAR(FilterOption("Small Car"))
}

enum class CarFuelType(val fuelType: FilterOption) {
    DIESEL(FilterOption("Diesel")),
    GASOLINE(FilterOption("Gasoline")),
    ELECTRIC(FilterOption("Electric")),
    HYBRID(FilterOption("Hybrid")),
    NATURAL_GAS(FilterOption("Natural Gas"))
}

enum class CarLocation(val location: FilterOption) {
    CLUJ(FilterOption("Cluj")),
    BUCHAREST(FilterOption("Bucharest")),
    TIMISOARA(FilterOption("Timisoara")),
    CONSTANTA(FilterOption("Constanta")),
    IASI(FilterOption("Iasi")),
    BRASOV(FilterOption("Brasov")),
    SIBIU(FilterOption("Sibiu")),
    ORADEA(FilterOption("Oradea")),
    CRAIOVA(FilterOption("Craiova")),
    GALATI(FilterOption("Galati")),
    PLOIESTI(FilterOption("Ploiesti")),
    ARAD(FilterOption("Arad")),
    BACAU(FilterOption("Bacau")),
    PITESTI(FilterOption("Pitesti")),
    TARGU_MURES(FilterOption("Targu Mures")),
    BAIA_MARE(FilterOption("Baia Mare")),
    CLUJ_NAPOCA(FilterOption("Cluj-Napoca")),
    SUCEAVA(FilterOption("Suceava"))
}

enum class CarGearboxType(val gearboxType: FilterOption) {
    MANUAL(FilterOption("Manual")),
    AUTOMATIC(FilterOption("Automatic"))
}

enum class CarColor(val color: FilterOption) {
    BLACK(FilterOption("Black")),
    WHITE(FilterOption("White")),
    SILVER(FilterOption("Silver")),
    GRAY(FilterOption("Gray")),
    RED(FilterOption("Red")),
    BLUE(FilterOption("Blue")),
    GREEN(FilterOption("Green")),
    YELLOW(FilterOption("Yellow")),
    ORANGE(FilterOption("Orange")),
    PURPLE(FilterOption("Purple"))
}

enum class CarCondition(val condition: FilterOption) {
    NEW(FilterOption("New")),
    USED(FilterOption("Used")),
}

enum class CarFeatures(val feature: FilterOption) {
    ABS(FilterOption("Anti-lock Braking System")),
    AdaptiveCruiseControl(FilterOption("Adaptive Cruise Control")),
    AmbientLighting(FilterOption("Ambient Lighting")),
    AppleCarPlay(FilterOption("Apple CarPlay")),
    AdaptiveCorneringLights(FilterOption("Adaptive Cornering Lights")),
    AlloyWheels(FilterOption("Alloy Wheels")),
    AndroidAuto(FilterOption("Android Auto")),
    Bluetooth(FilterOption("Bluetooth Connectivity")),
    BiXenonHeadlights(FilterOption("Bi-Xenon Headlights")),
    BlindSpotMonitoring(FilterOption("Blind Spot Monitoring")),
    CollisionAvoidance(FilterOption("Collision Avoidance System")),
    DriverAssistance(FilterOption("Driver Assistance Package")),
    LaneDepartureWarning(FilterOption("Lane Departure Warning")),
    LeatherSeats(FilterOption("Leather Seats")),
    NavigationSystem(FilterOption("Navigation System")),
    PanoramicRoof(FilterOption("Panoramic Roof")),
    ParkingSensors(FilterOption("Parking Sensors")),
    RearViewCamera(FilterOption("Rear-View Camera")),
    RemoteStart(FilterOption("Remote Start")),
    SatelliteRadio(FilterOption("Satellite Radio")),
    Sunroof(FilterOption("Sunroof")),
    ThirdRowSeating(FilterOption("Third Row Seating")),
    TouchscreenDisplay(FilterOption("Touchscreen Display")),
    TurbochargedEngine(FilterOption("Turbocharged Engine")),
    USBPorts(FilterOption("USB Ports")),
    VoiceRecognition(FilterOption("Voice Recognition")),
    WiFiHotspot(FilterOption("WiFi Hotspot")),
    WirelessCharging(FilterOption("Wireless Charging")),
    AllWheelDrive(FilterOption("All-Wheel Drive"))
}

enum class Type {
    URI,
    MAKE,
    MODELS,
    CATEGORY,
    MILEAGE,
    PRICE,
    LOCATION,
    CUBIC_CAPACITY,
    HP,
    FUEL,
    SEATS,
    GEAR_BOX,
    FIRST_REGISTRATION,
    COLOR,
    CONDITION,
    FEATURES
}

