package de.thomaskuenneth.cmpunitconverter

actual fun shouldUseScaffold(): Boolean = false

actual fun shouldShowAboutInSeparateWindow(): Boolean = true

actual val platformName: String = System.getProperty("os.name")
