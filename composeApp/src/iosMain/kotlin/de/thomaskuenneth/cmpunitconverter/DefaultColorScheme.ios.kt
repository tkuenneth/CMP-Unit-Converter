package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode

@Composable
actual fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme {
    return colorScheme(colorSchemeMode)
}
