package de.thomaskuenneth.cmpunitconverter.temperature

enum class TemperatureUnit {
    Celsius, Fahrenheit
}

class TemperatureRepository {

    fun getTemperatureSourceUnit(): TemperatureUnit = TemperatureUnit.Celsius

    fun setTemperatureSourceUnit(value: TemperatureUnit) {
    }

    fun getTemperatureDestinationUnit(): TemperatureUnit = TemperatureUnit.Celsius

    fun setTemperatureDestinationUnit(value: TemperatureUnit) {
    }

    fun getTemperature(): String = ""

    fun setTemperature(value: String) {
    }
}
