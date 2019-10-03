package com.pokumars.fitbo.data.network

import androidx.lifecycle.LiveData
import com.pokumars.fitbo.data.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import  kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val locationDao: LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider

): ForecastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{
            newCurrentWeather->persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(weather: Boolean): LiveData<out CurrentWeather> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (weather) currentWeatherDao.getWeather()
            else currentWeatherDao.getWeather()
        }
    }

    override suspend fun getLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO){
            return@withContext locationDao.getLocation()
        }
    }
    private  fun persistFetchedCurrentWeather(fetchedWeather: WeatherResponse){
        GlobalScope.launch (Dispatchers.IO){

            currentWeatherDao.upsert(fetchedWeather.current)
            locationDao.upsert(fetchedWeather.location)

        }

    }
    private suspend fun  initWeatherData(){
        val lastLocation =locationDao.getLocation().value
        if(lastLocation==null || locationProvider.hasLocationChanged(lastLocation)){
            fetchCurrentWeather()
            return
        }
        if (isFetchedNeeded(lastLocation.zonedDateTime))
            fetchCurrentWeather()
    }
    private suspend fun fetchCurrentWeather(){
       weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getProfferedLocationString(),Locale.getDefault().language)
    }
    private  fun  isFetchedNeeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}