package com.pokumars.fitbo.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID =0
@Entity(tableName = "current_weather")
data class CurrentWeather(
    @SerializedName("feelslike")
    val feelslike: Int,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("precip")
    val precip: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("temperature")
    val temperature: Int,
    @SerializedName("uv_index")
    val uvIndex: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    /*@SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,*/
    /*@SerializedName("weather_icons")
    val weatherIcons: List<String>,*/
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_WEATHER_ID
}