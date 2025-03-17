package de.thomaskuenneth.cmpunitconverter

import java.awt.Desktop
import java.awt.desktop.PreferencesHandler
import java.io.File
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

fun getConfigurationDir(): File = File(
    System.getProperty("user.home") ?: ".", when (operatingSystem) {
        OperatingSystem.MacOS -> "Library/Application Support/CMPUnitConverter"
        OperatingSystem.Windows -> "AppData\\Roaming\\CMPUnitConverter"
        else -> ".CMPUnitConverter"
    }
).also { it.mkdirs() }

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
