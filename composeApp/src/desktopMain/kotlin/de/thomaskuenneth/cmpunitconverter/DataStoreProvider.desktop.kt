package de.thomaskuenneth.cmpunitconverter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual fun getDataStore(key: String): DataStore<Preferences> = createDataStore(
    producePath = {
        File(System.getProperty("user.home"), dataStoreFileName(key)).absolutePath
    },
)
