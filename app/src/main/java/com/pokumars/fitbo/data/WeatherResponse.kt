package com.pokumars.fitbo.data


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: CurrentWeather,
    @SerializedName("currentLocationDao")
    val location: Location,
    @SerializedName("request")
    val request: Request
)