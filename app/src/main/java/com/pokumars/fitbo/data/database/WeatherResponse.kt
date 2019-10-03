package com.pokumars.fitbo.data.database


import com.google.gson.annotations.SerializedName
import com.pokumars.fitbo.data.database.CurrentWeather
import com.pokumars.fitbo.data.database.Location
import com.pokumars.fitbo.data.database.Request

data class WeatherResponse(
    @SerializedName("current")
    val current: CurrentWeather,
    @SerializedName("location")
    val location: Location,
    @SerializedName("request")
    val request: Request
)