package com.ceassar.test.data.datasource.model

data class WeatherResponse(
    val main: Main = Main(),
    val name: String = "",
    val wind: Wind = Wind()
) {


    data class Main(
        val feels_like: Double = 0.0,
        val grnd_level: Number = 0,
        val humidity: Number = 0,
        val pressure: Number = 0,
        val sea_level: Number = 0,
        val temp: Double = 0.0,
        val temp_max: Double = 0.0,
        val temp_min: Double = 0.0
    )


    data class Wind(
        val deg: Number = 0,
        val gust: Double = 0.0,
        val speed: Double = 0.0
    )
}