package de.thomaskuenneth.cmpunitconverter

import android.app.Application
import de.thomaskuenneth.cmpunitconverter.di.initKoin
import org.koin.android.ext.koin.androidContext

class CMPUnitConverterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CMPUnitConverterApp)
        }
    }
}
