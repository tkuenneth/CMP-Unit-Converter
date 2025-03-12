package de.thomaskuenneth.cmpunitconverter

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import de.thomaskuenneth.cmpunitconverter.di.initKoin
import org.koin.android.ext.koin.androidContext

class CMPUnitConverterApp : Application() {

    companion object {
        var applicationContext: Context = ContextWrapper(null)
    }

    override fun onCreate() {
        super.onCreate()
        CMPUnitConverterApp.applicationContext = this
        initKoin {
            androidContext(this@CMPUnitConverterApp)
        }
    }
}
