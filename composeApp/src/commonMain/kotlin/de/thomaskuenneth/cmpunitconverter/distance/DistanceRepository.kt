package de.thomaskuenneth.cmpunitconverter.distance

import de.thomaskuenneth.cmpunitconverter.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class DistanceUnit {
    Meter, Mile
}

private const val KEY = "distance"
private const val KEY_DISTANCE_SOURCE_UNIT = "keyDistanceSourceUnit"
private const val KEY_DISTANCE_DESTINATION_UNIT = "keyDistanceDestinationUnit"
private const val KEY_DISTANCE = "keyDistance"

class DistanceRepository : BaseRepository(KEY) {

    val sourceUnit: Flow<DistanceUnit>
        get() = getFlow(KEY_DISTANCE_SOURCE_UNIT, DistanceUnit.Meter)

    suspend fun setDistanceSourceUnit(value: DistanceUnit) {
        update(key = KEY_DISTANCE_SOURCE_UNIT, value = value.name)
    }

    val destinationUnit: Flow<DistanceUnit>
        get() = getFlow(KEY_DISTANCE_DESTINATION_UNIT, DistanceUnit.Meter)

    suspend fun setDistanceDestinationUnit(value: DistanceUnit) {
        update(key = KEY_DISTANCE_DESTINATION_UNIT, value = value.name)
    }

    val temperature: Flow<String>
        get() = getFlow(KEY_DISTANCE, "")

    suspend fun setDistance(value: String) {
        update(key = KEY_DISTANCE, value = value)
    }

    private fun getFlow(key: String, defaultValue: DistanceUnit): Flow<DistanceUnit> =
        getFlow(key, defaultValue.name).map { DistanceUnit.valueOf(it) }
}
