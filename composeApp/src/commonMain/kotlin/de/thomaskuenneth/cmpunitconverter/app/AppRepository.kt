package de.thomaskuenneth.cmpunitconverter.app

import de.thomaskuenneth.cmpunitconverter.AbstractBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


enum class ColorSchemeMode {
    System, Light, Dark
}

private const val KEY = "app"
private const val KEY_COLOR_SCHEME_MODE = "keyColorSchemeMode"
private const val KEY_SHOW_EXTENDED_ABOUT_DIALOG = "keyShowExtendedAboutDialog"

class AppRepository : AbstractBaseRepository(KEY) {

    val colorSchemeMode: Flow<ColorSchemeMode>
        get() = getFlow()

    val showExtendedAboutDialog: Flow<Boolean>
        get() = getFlow(KEY_SHOW_EXTENDED_ABOUT_DIALOG, true)

    suspend fun setColorSchemeMode(value: ColorSchemeMode) {
        update(key = KEY_COLOR_SCHEME_MODE, value = value.name)
    }

    suspend fun setShowExtendedAboutDialog(value: Boolean) {
        update(key = KEY_SHOW_EXTENDED_ABOUT_DIALOG, value = value)
    }

    private fun getFlow(): Flow<ColorSchemeMode> =
        getFlow(KEY_COLOR_SCHEME_MODE, ColorSchemeMode.System.name).map { ColorSchemeMode.valueOf(it) }
}
