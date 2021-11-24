package io.kokoichi.sample.sakamichiapp.presentation.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "sakamichi")

/**
 * Data storage that allows you to store key-value pairs or typed objects.
 */
object DataStoreManager {
    /**
     * Key sets for Data store.
     */
    const val KEY_IS_DEVELOPER = "is_developer"
    const val KEY_THEME_GROUP = "theme_type"

    suspend fun writeBoolean(context: Context, key: String, value: Boolean) =
        withContext(Dispatchers.IO) {

            val wrappedKey = booleanPreferencesKey(key)
            context.settingDataStore.edit {
                it[wrappedKey] = value
            }
        }

    suspend fun readBoolean(context: Context, key: String, default: Boolean = false): Boolean =
        withContext(Dispatchers.IO) {

            val wrappedKey = booleanPreferencesKey(key)
            val valueFlow: Flow<Boolean> = context.settingDataStore.data.map {
                it[wrappedKey] ?: default
            }
            return@withContext valueFlow.first()
        }

    suspend fun writeString(context: Context, key: String, value: String) =
        withContext(Dispatchers.IO) {

            val wrappedKey = stringPreferencesKey(key)
            context.settingDataStore.edit {
                it[wrappedKey] = value
            }
        }

    suspend fun readString(context: Context, key: String, default: String = ""): String =
        withContext(Dispatchers.IO) {

            val wrappedKey = stringPreferencesKey(key)
            val valueFlow: Flow<String> = context.settingDataStore.data.map {
                it[wrappedKey] ?: default
            }
            return@withContext valueFlow.first()
        }
}
