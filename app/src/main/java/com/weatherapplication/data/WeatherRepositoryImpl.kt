package com.weatherapplication.data

import com.weatherapplication.data.model.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService) :
    WeatherRepository {
    override suspend fun fetchCurrentCityWeather(params: Map<String, String>): Weather {
        return weatherApiService.fetchCityCurrentWeather(params)
    }
}