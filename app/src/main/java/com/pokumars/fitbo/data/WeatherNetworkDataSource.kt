package com.pokumars.fitbo.data

import androidx.lifecycle.LiveData

interface WeatherNetworkDataSource {
        val downloadedCurrentWeather: LiveData<WeatherResponse>

        suspend fun fetchCurrentWeather(
            location: String,
            languageCode: String
        )


}