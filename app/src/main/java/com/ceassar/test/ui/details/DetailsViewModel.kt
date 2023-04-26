package com.ceassar.test.ui.details

import androidx.lifecycle.MutableLiveData
import com.ceassar.test.data.datasource.model.CityEntity
import com.ceassar.test.data.datasource.model.WeatherResponse
import com.ceassar.test.data.datasource.repository.WeatherRepository
import com.ceassar.test.ui.base.BaseViewModel

class DetailsViewModel(val dto: CityEntity, private val weatherRepository: WeatherRepository) :
    BaseViewModel() {

    val weatherLiveData = MutableLiveData<WeatherResponse>()

    init {
        getWeatherData()
    }

    fun getWeatherData() {
        launchAsync {
            val response = weatherRepository.getWeather(dto.coord.lat, dto.coord.lon)
            weatherLiveData.postValue(response.body())
        }
    }

}