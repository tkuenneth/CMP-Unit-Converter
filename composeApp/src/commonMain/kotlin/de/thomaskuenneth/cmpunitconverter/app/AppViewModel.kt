package de.thomaskuenneth.cmpunitconverter.app

import androidx.lifecycle.ViewModel
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import de.thomaskuenneth.cmpunitconverter.shouldShowAboutInSeparateWindow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AboutVisibility {
    Hidden, Window, Sheet
}

data class UiState(
    val currentDestination: AppDestinations, val aboutVisibility: AboutVisibility
)

class AppViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            currentDestination = AppDestinations.Temperature, aboutVisibility = AboutVisibility.Hidden
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun setCurrentDestination(destination: AppDestinations) {
        _uiState.update { state -> state.copy(currentDestination = destination) }
    }

    fun setShouldShowAbout(shouldShowAbout: Boolean) {
        _uiState.update { state ->
            state.copy(
                aboutVisibility = when (shouldShowAbout) {
                    false -> AboutVisibility.Hidden
                    true -> if (shouldShowAboutInSeparateWindow()) AboutVisibility.Window else AboutVisibility.Sheet
                }
            )
        }
    }
}
