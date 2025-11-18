package com.example.superfruits.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfruits.data.PuppyStats
import com.example.superfruits.data.PuppyStatsStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PuppyViewModel(application: Application) : AndroidViewModel(application) {

    private val statsStore = PuppyStatsStore(application)

    private val _puppyStats = MutableStateFlow(PuppyStats())
    val puppyStats: StateFlow<PuppyStats> = _puppyStats.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            statsStore.statsFlow.collect { stats ->
                _puppyStats.value = stats
            }
        }
    }

    fun feedHealthyFood() {
        viewModelScope.launch {
            val current = _puppyStats.value
            val updated = PuppyStats(
                strength = current.strength + 5,
                speed = current.speed + 3,
                intelligence = current.intelligence + 5,
                health = current.health + 10
            ).clamp()

            statsStore.updateStats(updated)
        }
    }

    fun feedJunkFood() {
        viewModelScope.launch {
            val current = _puppyStats.value
            val updated = PuppyStats(
                strength = current.strength - 3,
                speed = current.speed - 3,
                intelligence = current.intelligence - 2,
                health = current.health - 8
            ).clamp()

            statsStore.updateStats(updated)
        }
    }

    fun feedBalancedFood() {
        viewModelScope.launch {
            val current = _puppyStats.value
            val updated = PuppyStats(
                strength = current.strength + 2,
                speed = current.speed + 2,
                intelligence = current.intelligence + 2,
                health = current.health + 3
            ).clamp()

            statsStore.updateStats(updated)
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            statsStore.reset()
        }
    }
}