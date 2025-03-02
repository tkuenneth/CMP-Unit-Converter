package de.thomaskuenneth.cmpunitconverter

import platform.UIKit.UIDevice

actual fun shouldUseScaffold(): Boolean = true

actual val platformName: String = UIDevice.currentDevice().systemName()
