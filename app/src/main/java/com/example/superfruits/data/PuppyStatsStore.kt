package com.example.superfruits.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "puppy_stats")

class PuppyStatsStore(private val context: Context) {

    companion object {
        private val STRENGTH_KEY = intPreferencesKey("strength")
        private val SPEED_KEY = intPreferencesKey("speed")
        private val INTELLIGENCE_KEY = intPreferencesKey("intelligence")
        private val HEALTH_KEY = intPreferencesKey("health")
    }

    val statsFlow: Flow<PuppyStats> = context.dataStore.data
        .map { preferences ->
            PuppyStats(
                strength = preferences[STRENGTH_KEY] ?: 50,
                speed = preferences[SPEED_KEY] ?: 50,
                intelligence = preferences[INTELLIGENCE_KEY] ?: 50,
                health = preferences[HEALTH_KEY] ?: 50
            )
        }

    suspend fun updateStats(stats: PuppyStats) {
        context.dataStore.edit { preferences ->
            preferences[STRENGTH_KEY] = stats.strength
            preferences[SPEED_KEY] = stats.speed
            preferences[INTELLIGENCE_KEY] = stats.intelligence
            preferences[HEALTH_KEY] = stats.health
        }
    }

    suspend fun reset() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

data class PuppyStats(
    val strength: Int = 50,
    val speed: Int = 50,
    val intelligence: Int = 50,
    val health: Int = 50
) {
    fun clamp() = copy(
        strength = strength.coerceIn(0, 100),
        speed = speed.coerceIn(0, 100),
        intelligence = intelligence.coerceIn(0, 100),
        health = health.coerceIn(0, 100)
    )
}