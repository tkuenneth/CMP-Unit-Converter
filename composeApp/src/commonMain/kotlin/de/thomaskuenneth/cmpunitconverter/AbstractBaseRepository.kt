package de.thomaskuenneth.cmpunitconverter

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

abstract class AbstractBaseRepository(key: String) {

    private val dataStore = getDataStore(key)

    protected fun getFlow(key: String, defaultValue: String): Flow<String> {
        return dataStore.data.map {
            it[stringPreferencesKey(key)] ?: defaultValue
        }.flowOn(Dispatchers.IO)
    }

    protected fun getFlow(key: String, defaultValue: Float): Flow<Float> {
        return dataStore.data.map {
            it[floatPreferencesKey(key)] ?: defaultValue
        }.flowOn(Dispatchers.IO)
    }

    protected suspend fun update(key: String, value: String) {
        return withContext(Dispatchers.IO) {
            dataStore.edit {
                it[stringPreferencesKey(key)] = value
            }
        }
    }

    protected suspend fun update(key: String, value: Float) {
        return withContext(Dispatchers.IO) {
            dataStore.edit {
                it[floatPreferencesKey(key)] = value
            }
        }
    }
}
