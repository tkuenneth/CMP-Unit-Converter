package de.thomaskuenneth.cmpunitconverter

import java.awt.Desktop
import java.awt.desktop.AboutHandler
import java.awt.desktop.PreferencesHandler
import java.io.IOException
import java.net.URI

enum class OperatingSystem {
    Linux, Windows, MacOS, Unknown;
}

val operatingSystem = platformName.lowercase().let { platformName ->
    if (platformName.contains("mac os x")) {
        OperatingSystem.MacOS
    } else if (platformName.contains("windows")) {
        OperatingSystem.Windows
    } else if (platformName.contains("linux")) {
        OperatingSystem.Linux
    } else {
        OperatingSystem.Unknown
    }
}

fun Desktop.installPreferencesHandler(handler: PreferencesHandler) {
    if (isSupported(Desktop.Action.APP_PREFERENCES)) {
        setPreferencesHandler(handler)
    }
}

fun Desktop.installAboutHandler(handler: AboutHandler?) {
    if (isSupported(Desktop.Action.APP_ABOUT)) {
        setAboutHandler(handler)
    }
}

fun browse(url: String, completionHandler: (Boolean) -> Unit = {}) {
    with(Desktop.getDesktop()) {
        val result = if (isSupported(Desktop.Action.BROWSE)) {
            try {
                browse(URI.create(url))
                true
            } catch (_: IOException) {
                false
            } catch (_: SecurityException) {
                false
            }
        } else false
        completionHandler(result)
    }
}
