package de.thomaskuenneth.cmpunitconverter

import platform.UIKit.UIDevice

actual fun shouldUseScaffold(): Boolean = true

actual fun shouldShowAboutInSeparateWindow(): Boolean = false

actual fun shouldShowSettingsInSeparateWindow(): Boolean = false

actual val platformName: String = UIDevice.currentDevice().systemName()
