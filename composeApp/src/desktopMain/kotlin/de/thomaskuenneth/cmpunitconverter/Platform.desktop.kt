package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.colorScheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.text.NumberFormat
import java.text.ParseException

actual fun shouldUseScaffold(): Boolean = false

actual fun shouldShowAboutInSeparateWindow(): Boolean = true

actual fun shouldShowSettingsInSeparateWindow(): Boolean = true

actual val platformName: String = System.getProperty("os.name") ?: ""

@Composable
actual fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme {
    return colorScheme(colorSchemeMode)
}

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

actual fun getDataStore(key: String): DataStore<Preferences> = createDataStore(
    producePath = {
        File(System.getProperty("user.home"), dataStoreFileName(key)).absolutePath
    },
)

actual fun Float.convertToLocalizedString(digits: Int): String {
    with(NumberFormat.getInstance()) {
        isGroupingUsed = true
        if (digits != -1) {
            maximumFractionDigits = digits
        }
        return try {
            if (!isNaN()) format(toFloat()) else ""
        } catch (_: IllegalArgumentException) {
            ""
        }
    }
}

actual fun String.convertLocalizedStringToFloat(): Float {
    with(NumberFormat.getInstance()) {
        isGroupingUsed = true
        return try {
            parse(this@convertLocalizedStringToFloat)?.toFloat() ?: Float.NaN
        } catch (_: ParseException) {
            Float.NaN
        }
    }
}

actual fun openInBrowser(url: String) = browse(url)
