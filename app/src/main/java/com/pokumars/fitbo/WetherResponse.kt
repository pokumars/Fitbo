package com.pokumars.fitbo

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherModel {

    const val URL ="https://api.openweathermap.org/data/2.5/"
    object Model{
        data class Result(@SerializedName("main") val result:Main)
        data class Main(@SerializedName("temp") val temperature:Float)
    }

    interface Service {
        @GET("weather?")
        fun dailyTemp(
            @Query("lat") lat: String,
            @Query("lon") lon:String,
            @Query("appid") appid:String
        ): Call<Model.Result>
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(Service::class.java)!!

}