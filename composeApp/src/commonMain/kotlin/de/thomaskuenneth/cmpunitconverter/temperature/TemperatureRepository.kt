package de.thomaskuenneth.cmpunitconverter.temperature

import de.thomaskuenneth.cmpunitconverter.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class TemperatureUnit {
    Celsius, Fahrenheit
}

private const val KEY_TEMPERATURE_SOURCE_UNIT = "keyTemperatureSourceUnit"
private const val KEY_TEMPERATURE_DESTINATION_UNIT = "keyTemperatureDestinationUnit"
private const val KEY_TEMPERATURE = "keyTemperature"

class TemperatureRepository : BaseRepository() {

    val sourceUnit: Flow<TemperatureUnit>
        get() = getFlow(KEY_TEMPERATURE_SOURCE_UNIT, TemperatureUnit.Celsius)

    suspend fun setTemperatureSourceUnit(value: TemperatureUnit) {
        update(key = KEY_TEMPERATURE_SOURCE_UNIT, value = value.name)
    }

    val destinationUnit: Flow<TemperatureUnit>
        get() = getFlow(KEY_TEMPERATURE_DESTINATION_UNIT, TemperatureUnit.Celsius)

    suspend fun setTemperatureDestinationUnit(value: TemperatureUnit) {
        update(key = KEY_TEMPERATURE_DESTINATION_UNIT, value = value.name)
    }

    val temperature: Flow<String>
        get() = getFlow(KEY_TEMPERATURE, "")

    suspend fun setTemperature(value: String) {
        update(key = KEY_TEMPERATURE, value = value)
    }

    private fun getFlow(key: String, defaultValue: TemperatureUnit): Flow<TemperatureUnit> =
        getFlow(key, defaultValue.name).map { TemperatureUnit.valueOf(it) }
}
