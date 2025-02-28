package de.thomaskuenneth.cmpunitconverter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun getDataStore(key: String): DataStore<Preferences> = createDataStore(
    producePath = {
        CMPUnitConverterApp.applicationContext.filesDir.resolve(dataStoreFileName(key)).absolutePath
    },
)
