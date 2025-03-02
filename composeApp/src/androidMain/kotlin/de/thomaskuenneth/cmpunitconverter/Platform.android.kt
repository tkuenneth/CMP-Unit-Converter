package de.thomaskuenneth.cmpunitconverter

import android.os.Build

actual fun shouldUseScaffold(): Boolean = true

actual val platformName: String = "Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"
