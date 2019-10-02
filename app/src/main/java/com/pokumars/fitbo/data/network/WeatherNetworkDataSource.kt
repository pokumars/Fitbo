package com.pokumars.fitbo.data.network

import androidx.lifecycle.LiveData
import com.pokumars.fitbo.data.database.WeatherResponse

interface WeatherNetworkDataSource {
        val downloadedCurrentWeather: LiveData<WeatherResponse>

        suspend fun fetchCurrentWeather(
            location: String,
            languageCode: String
        )


}