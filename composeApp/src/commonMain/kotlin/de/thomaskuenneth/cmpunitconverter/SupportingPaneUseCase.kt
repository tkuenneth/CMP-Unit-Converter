package de.thomaskuenneth.cmpunitconverter

import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.distance.DistanceUnit
import de.thomaskuenneth.cmpunitconverter.distance.URL_WIKIPEDIA_METER
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureUnit
import de.thomaskuenneth.cmpunitconverter.temperature.URL_WIKIPEDIA_CELSIUS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.enums.EnumEntries

class TemperatureSupportingPaneUseCase : SupportingPaneUseCase(
    info = Res.string.celsius_info, lastClicked = Res.string.celsius, url = URL_WIKIPEDIA_CELSIUS
) {
    override val entries = TemperatureUnit.entries

    override fun String.getUnit(): Enum<*> = TemperatureUnit.valueOf(this)
}

class DistanceSupportingPaneUseCase : SupportingPaneUseCase(
    info = Res.string.meter_info, lastClicked = Res.string.meter, url = URL_WIKIPEDIA_METER
) {
    override val entries = DistanceUnit.entries

    override fun String.getUnit(): Enum<*> = DistanceUnit.valueOf(this)
}

abstract class SupportingPaneUseCase(
    info: StringResource, lastClicked: StringResource, url: String
) : KoinComponent {

    data class UiState(
        val info: StringResource, val lastClicked: StringResource, val url: String, val elements: List<HistoryEntity>
    )

    private val historyRepository: HistoryRepository by inject<HistoryRepository>()

    private val mutableStateFlow: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            info = info, lastClicked = lastClicked, url = url, elements = emptyList()
        )
    )
    val stateFlow: StateFlow<UiState> = mutableStateFlow.asStateFlow()

    abstract val entries: EnumEntries<*>

    abstract fun String.getUnit(): Enum<*>

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            historyRepository.elements.collect { elements ->
                mutableStateFlow.update { uiState ->
                    uiState.copy(
                        elements = elements.filter { element ->
                            entries.find { entry ->
                                entry.name == element.sourceUnit
                            } != null
                        })
                }
            }
        }
    }

    fun update(
        info: StringResource, lastClicked: StringResource, url: String
    ) {
        mutableStateFlow.update { state -> state.copy(info = info, lastClicked = lastClicked, url = url) }
    }

    fun openInBrowser() {
        openInBrowser(mutableStateFlow.value.url)
    }

    suspend fun persist(sourceUnit: Enum<*>, sourceValue: Float, destinationUnit: Enum<*>, destinationValue: Float) {
        historyRepository.persist(
            sourceUnit = sourceUnit,
            sourceValue = sourceValue,
            destinationUnit = destinationUnit,
            destinationValue = destinationValue
        )
    }
}
