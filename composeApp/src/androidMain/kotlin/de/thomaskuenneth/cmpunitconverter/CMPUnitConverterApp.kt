package de.thomaskuenneth.cmpunitconverter

import android.app.Application
import android.content.Context
import android.content.ContextWrapper

class CMPUnitConverterApp : Application() {

    companion object {
        var applicationContext: Context = ContextWrapper(null)
    }

    override fun onCreate() {
        super.onCreate()
        CMPUnitConverterApp.applicationContext = this
    }
}
