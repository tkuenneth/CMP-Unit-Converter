package de.thomaskuenneth.cmpunitconverter.temperature

import de.thomaskuenneth.cmpunitconverter.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class TemperatureUnit {
    Celsius, Fahrenheit
}

private const val KEY = "temperature"
private const val KEY_TEMPERATURE_SOURCE_UNIT = "keyTemperatureSourceUnit"
private const val KEY_TEMPERATURE_DESTINATION_UNIT = "keyTemperatureDestinationUnit"
private const val KEY_TEMPERATURE_AS_FLOAT = "keyTemperatureAsFloat"

class TemperatureRepository : BaseRepository(KEY) {

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

    val temperature: Flow<Float>
        get() = getFlow(KEY_TEMPERATURE_AS_FLOAT, Float.NaN)

    suspend fun setTemperature(value: Float) {
        update(key = KEY_TEMPERATURE_AS_FLOAT, value = value)
    }

    private fun getFlow(key: String, defaultValue: TemperatureUnit): Flow<TemperatureUnit> =
        getFlow(key, defaultValue.name).map { TemperatureUnit.valueOf(it) }
}
