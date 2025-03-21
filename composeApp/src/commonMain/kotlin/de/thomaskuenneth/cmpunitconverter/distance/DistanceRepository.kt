package de.thomaskuenneth.cmpunitconverter.distance

import de.thomaskuenneth.cmpunitconverter.BaseRepository
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val KEY = "distance"
private const val KEY_DISTANCE_SOURCE_UNIT = "keyDistanceSourceUnit"
private const val KEY_DISTANCE_DESTINATION_UNIT = "keyDistanceDestinationUnit"
private const val KEY_DISTANCE_AS_FLOAT = "keyDistanceAsFloat"

class DistanceRepository : BaseRepository(KEY) {

    val sourceUnit: Flow<UnitsAndScales>
        get() = getFlow(KEY_DISTANCE_SOURCE_UNIT, UnitsAndScales.Meter)

    suspend fun setDistanceSourceUnit(value: UnitsAndScales) {
        update(key = KEY_DISTANCE_SOURCE_UNIT, value = value.name)
    }

    val destinationUnit: Flow<UnitsAndScales>
        get() = getFlow(KEY_DISTANCE_DESTINATION_UNIT, UnitsAndScales.Meter)

    suspend fun setDistanceDestinationUnit(value: UnitsAndScales) {
        update(key = KEY_DISTANCE_DESTINATION_UNIT, value = value.name)
    }

    val distance: Flow<Float>
        get() = getFlow(KEY_DISTANCE_AS_FLOAT, Float.NaN)

    suspend fun setDistance(value: Float) {
        update(key = KEY_DISTANCE_AS_FLOAT, value = value)
    }

    private fun getFlow(key: String, defaultValue: UnitsAndScales): Flow<UnitsAndScales> =
        getFlow(key, defaultValue.name).map { UnitsAndScales.valueOf(it) }
}
