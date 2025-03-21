package de.thomaskuenneth.cmpunitconverter

import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    val sourceUnit: Flow<UnitsAndScales>

    suspend fun setSourceUnit(value: UnitsAndScales)

    val destinationUnit: Flow<UnitsAndScales>

    suspend fun setDestinationUnit(value: UnitsAndScales)

    val value: Flow<Float>

    suspend fun setValue(value: Float)
}
