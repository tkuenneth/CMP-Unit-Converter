package de.thomaskuenneth.cmpunitconverter

import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class HistoryRepository {

    private val historyDao = getRoomDatabase(getDatabaseBuilder()).getHistoryDao()

    val elements = historyDao.getAllAsFlow().map { it }

    suspend fun persist(
        sourceUnit: UnitsAndScales, sourceValue: Float, destinationUnit: UnitsAndScales, destinationValue: Float
    ) {
        historyDao.insert(
            HistoryEntity(
                sourceUnit = sourceUnit,
                sourceValue = sourceValue,
                destinationUnit = destinationUnit,
                destinationValue = destinationValue,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        )
    }
}
