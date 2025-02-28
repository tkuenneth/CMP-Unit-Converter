package de.thomaskuenneth.cmpunitconverter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun dataStoreFileName(key: String) = "de.thomaskuenneth.cmpunitconverter.$key.preferences_pb"

fun createDataStore(producePath: () -> String): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    produceFile = { producePath().toPath() })

expect fun getDataStore(key: String): DataStore<Preferences>
