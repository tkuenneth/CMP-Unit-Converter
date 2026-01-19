package de.thomaskuenneth.cmpunitconverter

import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HistoryRepository(private val historyDao: HistoryDao) {

    val elements = historyDao.getAllAsFlow().map { it }

    @OptIn(ExperimentalTime::class)
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

    suspend fun clearConversionsHistory() {
        historyDao.deleteAll()
    }
}
