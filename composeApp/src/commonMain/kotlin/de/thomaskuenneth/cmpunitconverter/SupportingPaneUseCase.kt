package de.thomaskuenneth.cmpunitconverter

import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.distance.URL_WIKIPEDIA_METER
import de.thomaskuenneth.cmpunitconverter.temperature.URL_WIKIPEDIA_CELSIUS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.StringResource

class TemperatureSupportingPaneUseCase : SupportingPaneUseCase(
    info = Res.string.celsius_info, lastClicked = Res.string.celsius, url = URL_WIKIPEDIA_CELSIUS
)

class DistanceSupportingPaneUseCase : SupportingPaneUseCase(
    info = Res.string.meter_info, lastClicked = Res.string.meter, url = URL_WIKIPEDIA_METER
)

abstract class SupportingPaneUseCase(
    info: StringResource, lastClicked: StringResource, url: String
) {

    data class UiState(
        val info: StringResource, val lastClicked: StringResource, val url: String
    )

    private val mutableStateFlow: MutableStateFlow<UiState> =
        MutableStateFlow(UiState(info = info, lastClicked = lastClicked, url = url))
    val stateFlow: StateFlow<UiState> = mutableStateFlow.asStateFlow()

    fun update(
        info: StringResource, lastClicked: StringResource, url: String
    ) {
        mutableStateFlow.update { state -> state.copy(info = info, lastClicked = lastClicked, url = url) }
    }

    fun openInBrowser() {
        openInBrowser(mutableStateFlow.value.url)
    }
}
