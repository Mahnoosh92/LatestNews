package com.mahnoosh.datastore.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object PreferencesKeys {
    val IS_ONBOARDING_SHOWN = booleanPreferencesKey("is_onboarding_shown")
}

class UserPreferenceRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    val isOnBoardingShownFlow: Flow<Boolean> = dataStore
        .data
        .map { preferences ->
            // Provide a default value if the key is not set
            preferences[PreferencesKeys.IS_ONBOARDING_SHOWN] == true
        }

    suspend fun saveOnBoardingStatus(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_ONBOARDING_SHOWN] = isEnabled
        }
    }
}