package de.thomaskuenneth.cmpunitconverter

import java.awt.Desktop
import java.awt.desktop.PreferencesHandler

fun Desktop.installPreferencesHandler(handler: PreferencesHandler) {
    if (isSupported(Desktop.Action.APP_PREFERENCES)) {
        setPreferencesHandler(handler)
    }
}
