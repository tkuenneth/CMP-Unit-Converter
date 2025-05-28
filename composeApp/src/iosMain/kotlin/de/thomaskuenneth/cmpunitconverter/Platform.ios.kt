package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.colorScheme
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.numberWithFloat
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

actual fun shouldUseScaffold(): Boolean = true

actual fun shouldShowAboutInSeparateWindow(): Boolean = false

actual fun shouldShowExtendedAboutDialogCheckbox(): Boolean = false

actual fun shouldShowSettingsInSeparateWindow(): Boolean = false

actual val platformName: String = UIDevice.currentDevice().systemName()

@Composable
actual fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme {
    return colorScheme(colorSchemeMode)
}

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
}

actual fun getDataStore(key: String): DataStore<Preferences> = createDataStore(
    producePath = {
        "${getDirectoryForType(DirectoryType.Configuration)}/${dataStoreFileName(key)}"
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

actual fun openInBrowser(url: String, completionHandler: (Boolean) -> Unit) {
    NSURL.URLWithString(url)?.let {
        UIApplication.sharedApplication.openURL(
            url = it, options = emptyMap<Any?, Any>(), completionHandler = completionHandler
        )
    }
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> =
    getDirectoryForType(DirectoryType.Database).let { dir ->
        Room.databaseBuilder<AppDatabase>(
            name = "$dir/CMPUnitConverter.db"
        )
    }

@OptIn(ExperimentalForeignApi::class)
actual fun getDirectoryForType(type: DirectoryType): String {
    val url = NSFileManager.defaultManager.URLForDirectory(
        directory = when (type) {
            DirectoryType.Configuration -> NSApplicationSupportDirectory
            DirectoryType.Database -> NSApplicationSupportDirectory
            DirectoryType.Files -> NSDocumentDirectory
        },
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null,
    )
    return url?.path ?: NSFileManager.defaultManager.currentDirectoryPath
}

actual fun Long.convertToLocalizedDate(): String {
    NSDateFormatter().apply {
        dateStyle = NSDateFormatterShortStyle
        locale = NSLocale.currentLocale
        return stringFromDate(NSDate.dateWithTimeIntervalSince1970(toDouble() / 1000.0))
    }
}

actual fun Long.convertToLocalizedTime(): String {
    NSDateFormatter().apply {
        timeStyle = NSDateFormatterShortStyle
        locale = NSLocale.currentLocale
        return stringFromDate(NSDate.dateWithTimeIntervalSince1970(toDouble() / 1000.0))
    }
}
