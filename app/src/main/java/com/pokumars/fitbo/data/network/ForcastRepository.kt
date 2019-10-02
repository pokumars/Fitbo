package com.pokumars.fitbo.data.network

import androidx.lifecycle.LiveData
import com.pokumars.fitbo.data.database.CurrentWeather

interface ForcastRepository {
    suspend fun getCurrentWeather(weather:Boolean):LiveData<out CurrentWeather>
}