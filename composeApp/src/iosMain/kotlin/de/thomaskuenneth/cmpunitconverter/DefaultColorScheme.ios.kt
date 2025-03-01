package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun defaultColorScheme() = with(isSystemInDarkTheme()) {
    when (this) {
        true -> darkColorScheme()

        false -> lightColorScheme()
    }
}
