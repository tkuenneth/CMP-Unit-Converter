package de.thomaskuenneth.cmpunitconverter

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    private val dataStore = getDataStore()

    protected fun getFlow(key: String, defaultValue: String): Flow<String> {
        return dataStore.data.map {
            it[stringPreferencesKey(key)] ?: defaultValue
        }.flowOn(Dispatchers.IO)
    }

    protected suspend fun update(key: String, value: String) {
        return withContext(Dispatchers.IO) {
            dataStore.edit {
                it[stringPreferencesKey(key)] = value
            }
        }
    }
}
