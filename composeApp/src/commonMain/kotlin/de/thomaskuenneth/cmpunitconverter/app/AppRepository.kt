package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.foundation.isSystemInDarkTheme
import de.thomaskuenneth.cmpunitconverter.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


enum class ColorSchemeMode {
    System, Light, Dark
}

private const val KEY = "app"
private const val KEY_COLOR_SCHEME_MODE = "keyColorSchemeMode"

class AppRepository : BaseRepository(KEY) {

    val colorSchemeMode: Flow<ColorSchemeMode>
        get() = getFlow(KEY_COLOR_SCHEME_MODE, ColorSchemeMode.System)

    suspend fun setColorSchemeMode(value: ColorSchemeMode) {
        update(key = KEY_COLOR_SCHEME_MODE, value = value.name)
    }

    private fun getFlow(key: String, defaultValue: ColorSchemeMode): Flow<ColorSchemeMode> =
        getFlow(key, defaultValue.name).map { ColorSchemeMode.valueOf(it) }
}
