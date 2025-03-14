package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.colorScheme
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*
import platform.UIKit.UIDevice

actual fun shouldUseScaffold(): Boolean = true

actual fun shouldShowAboutInSeparateWindow(): Boolean = false

actual fun shouldShowSettingsInSeparateWindow(): Boolean = false

actual val platformName: String = UIDevice.currentDevice().systemName()

@Composable
actual fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme {
    return colorScheme(colorSchemeMode)
}

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
}

@OptIn(ExperimentalForeignApi::class)
actual fun getDataStore(key: String): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/${dataStoreFileName(key)}"
    },
)

actual fun Float.convertToLocalizedString(digits: Int): String {
    with(NSNumberFormatter()) {
        numberStyle = NSNumberFormatterDecimalStyle
        locale = NSLocale.currentLocale()
        if (digits != -1) {
            maximumFractionDigits = digits.toULong()
        }
        return if (!isNaN()) {
            stringFromNumber(NSNumber.numberWithFloat(this@convertToLocalizedString)) ?: ""
        } else ""
    }
}

actual fun String.convertLocalizedStringToFloat(): Float {
    with(NSNumberFormatter()) {
        numberStyle = NSNumberFormatterDecimalStyle
        locale = NSLocale.currentLocale()
        return numberFromString(this@convertLocalizedStringToFloat)?.floatValue ?: Float.NaN
    }
}
