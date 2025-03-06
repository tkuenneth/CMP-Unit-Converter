package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode

@Composable
expect fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme

@Composable
fun ColorSchemeMode.isDark(): Boolean = when (this) {
    ColorSchemeMode.Dark -> true
    ColorSchemeMode.Light -> false
    ColorSchemeMode.System -> isSystemInDarkTheme()
}

@Composable
fun colorScheme(colorSchemeMode: ColorSchemeMode) = when (colorSchemeMode.isDark()) {
    false -> lightColorScheme()

    true -> darkColorScheme()
}
