package com.example.basedemo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.basedemo.utils.Constants.DEVICE_ID
import com.example.basedemo.utils.Constants.DEVICE_TOKEN
import com.example.basedemo.utils.Constants.HASH_DIGEST
import com.example.basedemo.utils.Constants.KEY_AUTH_OKEN
import com.example.basedemo.utils.Constants.KEY_PERMISSION_CHECK
import com.example.basedemo.utils.Constants.TIMESTAMP
import com.example.basedemo.utils.Constants.USER_MOBILE_NUMBER
import com.example.basedemo.utils.Constants.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "loanSDK"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreManager(private val context: Context) {

    companion object {
        val userMobile = stringPreferencesKey(name = USER_MOBILE_NUMBER)
        val permissionsCheck = intPreferencesKey(name = KEY_PERMISSION_CHECK)
        val authToken = stringPreferencesKey(name = KEY_AUTH_OKEN)
        val hashDigest = stringPreferencesKey(name = HASH_DIGEST)
        val timeStamp = stringPreferencesKey(name = TIMESTAMP)
        val deviceId = stringPreferencesKey(name = DEVICE_ID)
        val deviceToken = stringPreferencesKey(name = DEVICE_TOKEN)
        val userName = stringPreferencesKey(name = USER_NAME)
    }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preference ->
            preference[userName] = name
        }
    }

    val getUserName: Flow<String> = context.dataStore.data
        .map { preferences ->
            (preferences[userName] ?: "")
        }


    suspend fun saveUserMobileNumber(mobile: String) {
        context.dataStore.edit { preference ->
            preference[userMobile] = mobile
        }
    }

    val getSaveUserMobileNumber: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[userMobile] ?: ""
        }

    suspend fun saveDeviceToken(token: String) {
        context.dataStore.edit { preference ->
            preference[deviceToken] = token
        }
    }

    val getDeviceId: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[deviceId] ?: ""
        }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preference ->
            preference[authToken] = token
        }
    }

    val getAuthToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[authToken] ?: ""
        }

    val getDeviceToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[deviceToken] ?: ""
        }

    suspend fun saveTimeStamp(timeStampString: String) {
        context.dataStore.edit { preference ->
            preference[timeStamp] = timeStampString
        }
    }

    val getTimeStamp: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[timeStamp] ?: ""
        }

    suspend fun saveHashDigest(hashDigestString: String) {
        context.dataStore.edit { preference ->
            preference[hashDigest] = hashDigestString
        }
    }

    val getHashDigest: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[hashDigest] ?: ""
        }

    suspend fun saveDeviceId(deviceIdString: String) {
        context.dataStore.edit { preference ->
            preference[deviceId] = deviceIdString
        }
    }


    suspend fun savePermissionsCheck(check: Int) {
        context.dataStore.edit { preference ->
            preference[permissionsCheck] = check
        }
    }

    val getPermissionsCheck: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[permissionsCheck] ?: 0
        }

    suspend fun clearAllPreference() {
        context.dataStore.edit {
            it.clear()
        }
    }

}