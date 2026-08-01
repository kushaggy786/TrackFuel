package com.trackfuel.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "trackfuel_preferences")

class SettingsDataSource(private val context: Context) {

    companion object {
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val DISCLAIMER_ACCEPTED_VERSION = intPreferencesKey("disclaimer_accepted_version")
        val THEME_MODE = stringPreferencesKey("theme_mode") // DARK, LIGHT, SYSTEM
        val WATER_GLASS_SIZE_ML = intPreferencesKey("water_glass_size_ml")
        val COUNT_EXERCISE_SEPARATELY = booleanPreferencesKey("count_exercise_separately")
        val SELECTED_DAY_ISO = stringPreferencesKey("selected_day_iso")
        val UNITS_PREFERENCE = stringPreferencesKey("units_preference") // METRIC, IMPERIAL
    }

    val onboardingComplete: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[ONBOARDING_COMPLETE] ?: false
    }

    suspend fun setOnboardingComplete(complete: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETE] = complete
        }
    }

    val disclaimerAcceptedVersion: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[DISCLAIMER_ACCEPTED_VERSION] ?: 0
    }

    suspend fun setDisclaimerAcceptedVersion(version: Int) {
        context.dataStore.edit { prefs ->
            prefs[DISCLAIMER_ACCEPTED_VERSION] = version
        }
    }

    val themeMode: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[THEME_MODE] ?: "DARK" // Default dark-first
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[THEME_MODE] = mode
        }
    }

    val waterGlassSizeMl: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[WATER_GLASS_SIZE_ML] ?: 250
    }

    suspend fun setWaterGlassSizeMl(sizeMl: Int) {
        context.dataStore.edit { prefs ->
            prefs[WATER_GLASS_SIZE_ML] = sizeMl
        }
    }

    val countExerciseSeparately: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[COUNT_EXERCISE_SEPARATELY] ?: true
    }

    suspend fun setCountExerciseSeparately(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[COUNT_EXERCISE_SEPARATELY] = enabled
        }
    }

    val selectedDayIso: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[SELECTED_DAY_ISO]
    }

    suspend fun setSelectedDayIso(isoDate: String?) {
        context.dataStore.edit { prefs ->
            if (isoDate == null) {
                prefs.remove(SELECTED_DAY_ISO)
            } else {
                prefs[SELECTED_DAY_ISO] = isoDate
            }
        }
    }

    val unitsPreference: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[UNITS_PREFERENCE] ?: "METRIC"
    }

    suspend fun setUnitsPreference(units: String) {
        context.dataStore.edit { prefs ->
            prefs[UNITS_PREFERENCE] = units
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
