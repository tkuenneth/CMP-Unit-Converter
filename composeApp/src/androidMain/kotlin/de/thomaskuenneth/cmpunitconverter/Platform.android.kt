package de.thomaskuenneth.cmpunitconverter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.format.DateFormat
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.isDark
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.text.NumberFormat
import java.text.ParseException

private val context: Context by inject(Context::class.java)

actual fun shouldUseScaffold(): Boolean = true

actual fun shouldShowAboutInSeparateWindow(): Boolean = false

actual fun shouldShowExtendedAboutDialogCheckbox(): Boolean = false

actual fun shouldShowSettingsInSeparateWindow(): Boolean = false

actual val platformName: String = "Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"

@Composable
actual fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme {
    val hasDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val context = LocalContext.current
    return when (colorSchemeMode.isDark()) {
        false -> {
            if (hasDynamicColor) {
                dynamicLightColorScheme(context)
            } else {
                lightColorScheme()
            }
        }

        true -> {
            if (hasDynamicColor) {
                dynamicDarkColorScheme(context)
            } else {
                darkColorScheme()
            }
        }
    }
}

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    androidx.activity.compose.BackHandler(enabled = enabled) { onBack() }
}

actual fun getDataStore(key: String): DataStore<Preferences> = createDataStore(
    producePath = {
        "${getDirectoryForType(DirectoryType.Configuration)}${File.separator}${dataStoreFileName(key)}"
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

actual fun openInBrowser(url: String, completionHandler: (Boolean) -> Unit) {
    val result = try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW, Uri.parse(url)
            ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        true
    } catch (_: ActivityNotFoundException) {
        false
    }
    completionHandler(result)
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> = Room.databaseBuilder<AppDatabase>(
    context = context,
    name = context.getDatabasePath("CMPUnitConverter.db").absolutePath
)

actual fun getDirectoryForType(type: DirectoryType): String = when (type) {
    DirectoryType.Database -> throw IllegalArgumentException("Use context.getDatabasePath() instead")
    else -> context.filesDir.absolutePath
}

actual fun Long.convertToLocalizedDate(): String = DateFormat.getDateFormat(context).format(this)

actual fun Long.convertToLocalizedTime(): String = DateFormat.getTimeFormat(context).format(this)
