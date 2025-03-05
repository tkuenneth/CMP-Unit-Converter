package de.thomaskuenneth.cmpunitconverter.app

import androidx.lifecycle.ViewModel
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val currentDestination: AppDestinations, val shouldShowAbout: Boolean
)

class AppViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            currentDestination = AppDestinations.Temperature, shouldShowAbout = false
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun setCurrentDestination(destination: AppDestinations) {
        _uiState.update { state -> state.copy(currentDestination = destination) }
    }

    fun setShouldShowAbout(shouldShowAbout: Boolean) {
        _uiState.update { state -> state.copy(shouldShowAbout = shouldShowAbout) }
    }
}
