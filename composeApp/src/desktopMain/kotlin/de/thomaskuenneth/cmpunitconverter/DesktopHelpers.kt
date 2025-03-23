package de.thomaskuenneth.cmpunitconverter

import java.awt.Desktop
import java.awt.desktop.PreferencesHandler
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

fun browse(url: String) {
    with(Desktop.getDesktop()) {
        if (isSupported(Desktop.Action.BROWSE)) {
            browse(URI.create(url))
        }
    }
}
