package de.thomaskuenneth.cmpunitconverter

enum class DistanceUnit {
    meters, miles
}

enum class TemperatureUnit {
    celsius, fahrenheit
}

class Repository {

    fun getDistanceUnit(): DistanceUnit = DistanceUnit.meters

    fun setDistanceUnit(value: DistanceUnit) {
    }

    fun getDistance(): String = ""

    fun setDistance(value: String) {
    }

    fun getTemperatureUnit(): TemperatureUnit = TemperatureUnit.celsius

    fun setTemperatureUnit(value: TemperatureUnit) {
    }

    fun getTemperature(): String = ""

    fun setTemperature(value: String) {
    }
}
