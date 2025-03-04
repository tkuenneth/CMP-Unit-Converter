package de.thomaskuenneth.cmpunitconverter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BackHandlerState(
    val enabled: Boolean, val onBack: () -> Unit
)

private val mutableBackHandlerState: MutableStateFlow<BackHandlerState> = MutableStateFlow(BackHandlerState(false) {})
val backHandlerState: StateFlow<BackHandlerState> = mutableBackHandlerState.asStateFlow()

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    LaunchedEffect(key1 = enabled, key2 = onBack) {
        mutableBackHandlerState.update { state -> state.copy(enabled = enabled, onBack = onBack) }
    }
}
