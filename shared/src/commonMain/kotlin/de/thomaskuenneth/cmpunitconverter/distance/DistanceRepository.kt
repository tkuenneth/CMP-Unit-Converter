package de.thomaskuenneth.cmpunitconverter.distance

import de.thomaskuenneth.cmpunitconverter.AbstractBaseRepository
import de.thomaskuenneth.cmpunitconverter.ConverterRepository
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import kotlinx.coroutines.flow.Flow

private const val KEY = "distance"
private const val KEY_DISTANCE_SOURCE_UNIT = "keyDistanceSourceUnit"
private const val KEY_DISTANCE_DESTINATION_UNIT = "keyDistanceDestinationUnit"
private const val KEY_DISTANCE_AS_FLOAT = "keyDistanceAsFloat"

class DistanceRepository : AbstractBaseRepository(KEY), ConverterRepository {

    override val sourceUnit: Flow<UnitsAndScales>
        get() = getFlow(KEY_DISTANCE_SOURCE_UNIT, UnitsAndScales.Meter)

    override suspend fun setSourceUnit(value: UnitsAndScales) {
        update(key = KEY_DISTANCE_SOURCE_UNIT, value = value.name)
    }

    override val destinationUnit: Flow<UnitsAndScales>
        get() = getFlow(KEY_DISTANCE_DESTINATION_UNIT, UnitsAndScales.Meter)

    override suspend fun setDestinationUnit(value: UnitsAndScales) {
        update(key = KEY_DISTANCE_DESTINATION_UNIT, value = value.name)
    }

    override val value: Flow<Float>
        get() = getFlow(KEY_DISTANCE_AS_FLOAT, Float.NaN)

    override suspend fun setValue(value: Float) {
        update(key = KEY_DISTANCE_AS_FLOAT, value = value)
    }
}
