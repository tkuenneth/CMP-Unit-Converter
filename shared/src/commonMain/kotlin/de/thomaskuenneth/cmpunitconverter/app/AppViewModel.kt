package de.thomaskuenneth.cmpunitconverter.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import de.thomaskuenneth.cmpunitconverter.shouldShowAboutInSeparateWindow
import de.thomaskuenneth.cmpunitconverter.shouldShowSettingsInSeparateWindow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class DialogOrSheetVisibility {
    Hidden, Sheet, Window
}

data class UiState(
    val currentDestination: AppDestinations,
    val aboutVisibility: DialogOrSheetVisibility,
    val settingsVisibility: DialogOrSheetVisibility,
    val colorSchemeMode: ColorSchemeMode,
    val showExtendedAboutDialog: Boolean,
)

class AppViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            currentDestination = AppDestinations.Temperature,
            aboutVisibility = DialogOrSheetVisibility.Hidden,
            settingsVisibility = DialogOrSheetVisibility.Hidden,
            colorSchemeMode = ColorSchemeMode.System,
            showExtendedAboutDialog = false,
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        repository.colorSchemeMode.onEach { colorSchemeMode -> setColorSchemeMode(colorSchemeMode) }
            .launchIn(viewModelScope)
        repository.showExtendedAboutDialog.onEach { showExtendedAboutDialog ->
            setShowExtendedAboutDialog(
                showExtendedAboutDialog
            )
        }.launchIn(viewModelScope)
    }

    fun setCurrentDestination(destination: AppDestinations) {
        _uiState.update { state -> state.copy(currentDestination = destination) }
    }

    fun setShouldShowAbout(shouldShowAbout: Boolean) {
        _uiState.update { state ->
            state.copy(
                aboutVisibility = when (shouldShowAbout) {
                    false -> DialogOrSheetVisibility.Hidden
                    true -> if (shouldShowAboutInSeparateWindow()) DialogOrSheetVisibility.Window else DialogOrSheetVisibility.Sheet
                }
            )
        }
    }

    fun setShouldShowSettings(shouldShowSettings: Boolean) {
        _uiState.update { state ->
            state.copy(
                settingsVisibility = when (shouldShowSettings) {
                    false -> DialogOrSheetVisibility.Hidden
                    true -> if (shouldShowSettingsInSeparateWindow()) DialogOrSheetVisibility.Window else DialogOrSheetVisibility.Sheet
                }
            )
        }
    }

    fun setColorSchemeMode(colorSchemeMode: ColorSchemeMode) {
        _uiState.update { state -> state.copy(colorSchemeMode = colorSchemeMode) }
        viewModelScope.launch {
            repository.setColorSchemeMode(colorSchemeMode)
        }
    }

    fun setShowExtendedAboutDialog(showExtendedAboutDialog: Boolean) {
        _uiState.update { state -> state.copy(showExtendedAboutDialog = showExtendedAboutDialog) }
        viewModelScope.launch {
            repository.setShowExtendedAboutDialog(showExtendedAboutDialog)
        }
    }
}
