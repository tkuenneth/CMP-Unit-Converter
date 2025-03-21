package de.thomaskuenneth.cmpunitconverter.temperature

import de.thomaskuenneth.cmpunitconverter.AbstractBaseRepository
import de.thomaskuenneth.cmpunitconverter.BaseRepository
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val KEY = "temperature"
private const val KEY_TEMPERATURE_SOURCE_UNIT = "keyTemperatureSourceUnit"
private const val KEY_TEMPERATURE_DESTINATION_UNIT = "keyTemperatureDestinationUnit"
private const val KEY_TEMPERATURE_AS_FLOAT = "keyTemperatureAsFloat"

class TemperatureRepository : AbstractBaseRepository(KEY), BaseRepository {

    override val sourceUnit: Flow<UnitsAndScales>
        get() = getFlow(KEY_TEMPERATURE_SOURCE_UNIT, UnitsAndScales.Celsius)

    override suspend fun setSourceUnit(value: UnitsAndScales) {
        update(key = KEY_TEMPERATURE_SOURCE_UNIT, value = value.name)
    }

    override val destinationUnit: Flow<UnitsAndScales>
        get() = getFlow(KEY_TEMPERATURE_DESTINATION_UNIT, UnitsAndScales.Celsius)

    override suspend fun setDestinationUnit(value: UnitsAndScales) {
        update(key = KEY_TEMPERATURE_DESTINATION_UNIT, value = value.name)
    }

    override val value: Flow<Float>
        get() = getFlow(KEY_TEMPERATURE_AS_FLOAT, Float.NaN)

    override suspend fun setValue(value: Float) {
        update(key = KEY_TEMPERATURE_AS_FLOAT, value = value)
    }

    private fun getFlow(key: String, defaultValue: UnitsAndScales): Flow<UnitsAndScales> =
        getFlow(key, defaultValue.name).map { UnitsAndScales.valueOf(it) }
}
