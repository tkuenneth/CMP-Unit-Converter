package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
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

expect fun Float.convertToLocalizedString(digits: Int = 1): String

expect fun String.convertLocalizedStringToFloat(): Float

expect fun openInBrowser(url: String, completionHandler: (Boolean) -> Unit = {})

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

enum class DirectoryType {
    Configuration, Database, Files
}

expect fun getDirectoryForType(type: DirectoryType): String

expect fun Long.convertToLocalizedDate(): String

expect fun Long.convertToLocalizedTime(): String
