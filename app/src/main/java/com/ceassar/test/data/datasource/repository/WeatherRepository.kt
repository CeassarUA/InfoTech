package com.ceassar.test.data.datasource.repository

import com.ceassar.test.data.datasource.remote.MainAPI
import com.ceassar.test.utils.Constans

class WeatherRepository(private val mainAPI: MainAPI) {

    suspend fun getWeather(lat: Double, lon: Double) =
        mainAPI.getWeather(lat, lon, Constans.weatherApiKey)

}