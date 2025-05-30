package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.colorScheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException

actual fun shouldUseScaffold(): Boolean = false

actual fun shouldShowAboutInSeparateWindow(): Boolean = true

actual fun shouldShowExtendedAboutDialogCheckbox(): Boolean = operatingSystem == OperatingSystem.MacOS

actual fun shouldShowSettingsInSeparateWindow(): Boolean = true

actual val platformName: String = StringBuilder().also {
    val osName = System.getProperty("os.name") ?: ""
    val osVersion = System.getProperty("os.version") ?: ""
    val javaVendorVersion = System.getProperty("java.vendor.version") ?: ""
    val javaVendor = System.getProperty("java.vendor") ?: ""
    val osArch = System.getProperty("os.arch") ?: ""
    it.append(" $osName $osVersion")
    it.appendLine()
    it.append("$javaVendor $javaVendorVersion ($osArch)")
}.toString()

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
        File(getDirectoryForType(DirectoryType.Configuration), dataStoreFileName(key)).absolutePath
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

actual fun openInBrowser(url: String, completionHandler: (Boolean) -> Unit) =
    browse(url = url, completionHandler = completionHandler)

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> =
    with(File(getDirectoryForType(DirectoryType.Database), "CMPUnitConverter.db")) {
        Room.databaseBuilder<AppDatabase>(
            name = absolutePath
        )
    }

actual fun getDirectoryForType(type: DirectoryType): String {
    val home = System.getProperty("user.home") ?: "."
    val path = mutableListOf<String>().apply {
        add(home)
        addAll(
            when (operatingSystem) {
                OperatingSystem.MacOS -> listOf("Library", "Application Support", "CMPUnitConverter")

                OperatingSystem.Windows -> listOf("AppData", "Roaming", "CMPUnitConverter")

                else -> listOf(".CMPUnitConverter")
            }
        )
        add(type.name)
    }.joinToString(File.separator)
    with(File(path).apply { mkdirs() }) {
        return if (exists() && isDirectory && canRead() && canWrite()) {
            absolutePath
        } else {
            home
        }
    }
}

actual fun Long.convertToLocalizedDate(): String = DateFormat.getDateInstance(DateFormat.SHORT).format(this)

actual fun Long.convertToLocalizedTime(): String = DateFormat.getTimeInstance(DateFormat.SHORT).format(this)
