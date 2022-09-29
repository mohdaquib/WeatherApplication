package com.weatherapplication.data

interface WeatherRepository {
    suspend fun fetchCurrentCityWeather(params: Map<String, String>): Weather
}