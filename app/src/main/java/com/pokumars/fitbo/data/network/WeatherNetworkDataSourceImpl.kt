package com.pokumars.fitbo.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pokumars.fitbo.data.WeatherApiService
import com.pokumars.fitbo.data.database.WeatherResponse
import java.lang.Exception

class WeatherNetworkDataSourceImpl(private val weatherApiService: WeatherApiService) :
    WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<WeatherResponse>()
    override val downloadedCurrentWeather: LiveData<WeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather =weatherApiService
                .getCurrentWeather(location,languageCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e:Exception){
            Log.d("connectivity","no connection")
        }
    }
}