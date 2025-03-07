package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode

expect fun shouldUseScaffold(): Boolean

expect fun shouldShowAboutInSeparateWindow(): Boolean

expect fun shouldShowSettingsInSeparateWindow(): Boolean

expect val platformName: String

@Composable
expect fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme

@Composable
expect fun BackHandler(enabled: Boolean, onBack: () -> Unit)
