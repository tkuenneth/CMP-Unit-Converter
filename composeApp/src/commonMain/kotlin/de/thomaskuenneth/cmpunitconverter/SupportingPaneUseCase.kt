package de.thomaskuenneth.cmpunitconverter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TemperatureSupportingPaneUseCase :
    SupportingPaneUseCase(entries = TemperatureUnit, initial = UnitsAndScales.Celsius)

class DistanceSupportingPaneUseCase : SupportingPaneUseCase(entries = DistanceUnit, initial = UnitsAndScales.Meter)

abstract class SupportingPaneUseCase(private val entries: List<UnitsAndScales>, initial: UnitsAndScales) :
    KoinComponent {

    data class UiState(
        val current: UnitsAndScales, val elements: List<HistoryEntity>
    )

    private val historyRepository: HistoryRepository by inject<HistoryRepository>()

    private val mutableStateFlow: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            current = initial, elements = emptyList()
        )
    )
    val stateFlow: StateFlow<UiState> = mutableStateFlow.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            historyRepository.elements.collect { elements ->
                mutableStateFlow.update { uiState ->
                    uiState.copy(
                        elements = elements.filter { element ->
                            entries.contains(element.sourceUnit)
                        })
                }
            }
        }
    }

    fun update(value: UnitsAndScales) {
        mutableStateFlow.update { state -> state.copy(current = value) }
    }

    fun openInBrowser() {
        openInBrowser(mutableStateFlow.value.current.url)
    }

    suspend fun persist(
        sourceUnit: UnitsAndScales,
        sourceValue: Float,
        destinationUnit: UnitsAndScales,
        destinationValue: Float
    ) {
        historyRepository.persist(
            sourceUnit = sourceUnit,
            sourceValue = sourceValue,
            destinationUnit = destinationUnit,
            destinationValue = destinationValue
        )
    }
}
