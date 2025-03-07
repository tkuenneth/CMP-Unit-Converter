package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import de.thomaskuenneth.cmpunitconverter.app.colorScheme

actual fun shouldUseScaffold(): Boolean = false

actual fun shouldShowAboutInSeparateWindow(): Boolean = true

actual fun shouldShowSettingsInSeparateWindow(): Boolean = true

actual val platformName: String = System.getProperty("os.name")

@Composable
actual fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme {
    return colorScheme(colorSchemeMode)
}
