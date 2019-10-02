package com.pokumars.fitbo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeather)

    @Query ("select * from current_weather where id =$CURRENT_WEATHER_ID")
    fun getWeather():LiveData<CurrentWeather>
}
@Dao
interface LocationDao{
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun upsert(location: Location)

    @Query("select * from currentLocationDao where id =$LOCATION_ID")
    fun getLocation():LiveData<Location>
    @Query("select * from currentLocationDao where id =$LOCATION_ID")
    fun getLocationNonLive(): Location?
}