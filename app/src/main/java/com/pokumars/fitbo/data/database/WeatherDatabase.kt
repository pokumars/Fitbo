package com.pokumars.fitbo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pokumars.fitbo.data.Converter

@Database(entities = [CurrentWeather::class, Location::class],version = 2)
@TypeConverters(Converter::class)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun currentLocationDao(): LocationDao

    companion object{
        @Volatile private var instance: WeatherDatabase? =null
        private val LOCK =Any()

        operator fun  invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDataBase(context).also { instance =it }
        }

        private fun buildDataBase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java,"weather.db")
                .build()

    }
}