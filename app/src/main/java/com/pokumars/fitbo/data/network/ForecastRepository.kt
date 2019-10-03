package com.pokumars.fitbo.data.network

import androidx.lifecycle.LiveData
import com.pokumars.fitbo.data.database.CurrentWeather
import com.pokumars.fitbo.data.database.Location

interface ForecastRepository {
    suspend fun getCurrentWeather(weather:Boolean):LiveData<out CurrentWeather>
    suspend fun getLocation():LiveData<Location>
}