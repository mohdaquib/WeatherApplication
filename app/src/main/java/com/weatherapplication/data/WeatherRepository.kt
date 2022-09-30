package com.weatherapplication.data

import com.weatherapplication.data.model.Weather

interface WeatherRepository {
    suspend fun fetchCurrentCityWeather(params: Map<String, String>): Weather
}