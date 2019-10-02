package com.pokumars.fitbo.data

import androidx.lifecycle.LiveData

interface ForcastRepository {
    suspend fun getCurrentWeather(weather:Boolean):LiveData<out CurrentWeather>
}