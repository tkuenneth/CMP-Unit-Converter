package de.thomaskuenneth.cmpunitconverter

import android.content.Context
import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.isDark
import org.koin.java.KoinJavaComponent.inject
import java.text.NumberFormat
import java.text.ParseException

private val context: Context by inject(Context::class.java)

actual fun shouldUseScaffold(): Boolean = true

actual fun shouldShowAboutInSeparateWindow(): Boolean = false

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
        context.filesDir.resolve(dataStoreFileName(key)).absolutePath
    },
)

actual fun Float.convertToLocalizedString(digits: Int): String {
    with(NumberFormat.getInstance()) {
        isGroupingUsed = true
        if (digits != -1) {
            maximumFractionDigits = digits
        }
        return try {
            format(toFloat())
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
