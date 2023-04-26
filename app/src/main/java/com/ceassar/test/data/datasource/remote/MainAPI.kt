package com.ceassar.test.data.datasource.remote

import com.ceassar.test.data.datasource.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {

    @GET("/data/2.5/weather?")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>

}