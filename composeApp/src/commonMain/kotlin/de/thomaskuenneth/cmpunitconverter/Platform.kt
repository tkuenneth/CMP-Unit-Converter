package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import de.thomaskuenneth.cmpunitconverter.app.ColorSchemeMode
import okio.Path.Companion.toPath

expect fun shouldUseScaffold(): Boolean

expect fun shouldShowAboutInSeparateWindow(): Boolean

expect fun shouldShowSettingsInSeparateWindow(): Boolean

expect val platformName: String

@Composable
expect fun defaultColorScheme(colorSchemeMode: ColorSchemeMode): ColorScheme

@Composable
expect fun BackHandler(enabled: Boolean, onBack: () -> Unit)

fun dataStoreFileName(key: String) = "de.thomaskuenneth.cmpunitconverter.$key.preferences_pb"

fun createDataStore(producePath: () -> String): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    produceFile = { producePath().toPath() })

expect fun getDataStore(key: String): DataStore<Preferences>
