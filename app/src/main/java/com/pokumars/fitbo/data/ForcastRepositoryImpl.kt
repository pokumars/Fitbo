package com.pokumars.fitbo.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import  kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForcastRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
): ForcastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{
            newCurrentWeather->persistFetachedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(weather: Boolean): LiveData<out CurrentWeather> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (weather) currentWeatherDao.getWeather()
            else currentWeatherDao.getWeather()
        }
    }
    private  fun persistFetachedCurrentWeather(fetachedWeather:WeatherResponse){
        GlobalScope.launch (Dispatchers.IO){
            currentWeatherDao.upsert(fetachedWeather.current)
        }
    }
    private suspend fun  initWeatherData(){
        if (isFetchedNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }
    private suspend fun fetchCurrentWeather(){
       weatherNetworkDataSource.fetchCurrentWeather("vantaa",Locale.getDefault().language)
    }
    private  fun  isFetchedNeeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}