package de.thomaskuenneth.cmpunitconverter

import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class HistoryRepository {

    private val historyDao = getRoomDatabase(getDatabaseBuilder()).getHistoryDao()

    val elements = historyDao.getAllAsFlow().map { it }

    suspend fun persist(sourceUnit: Enum<*>, sourceValue: Float, destinationUnit: Enum<*>, destinationValue: Float) {
        historyDao.insert(
            HistoryEntity(
                sourceUnit = sourceUnit.name,
                sourceValue = sourceValue,
                destinationUnit = destinationUnit.name,
                destinationValue = destinationValue,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        )
    }
}
