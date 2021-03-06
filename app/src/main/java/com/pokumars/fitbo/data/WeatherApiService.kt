package com.pokumars.fitbo.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pokumars.fitbo.data.database.WeatherResponse
import com.pokumars.fitbo.data.network.ConnectivityInterceptor
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY ="69945e47712e08d0cf8287eca7cf2779"
//http://api.weatherstack.com/current?access_key=7ed2f28760ab290c83db3fc13f3d0364&query=New%20York

interface WeatherApiService {
    @GET("current")
    fun getCurrentWeather(
        @Query("query")location:String,
        @Query("lang")languageCode:String="en"

    ): Deferred<WeatherResponse>

    companion object{
        operator fun invoke(
           connectivityInterceptor: ConnectivityInterceptor
        ):WeatherApiService{
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                val request=chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)

            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }

}