package com.weatherapplication.data

class WeatherRepositoryImpl(private val weatherApiService: WeatherApiService) : WeatherRepository {
    override suspend fun fetchCurrentCityWeather(params: Map<String, String>): Weather {
        return weatherApiService.fetchCityCurrentWeather(params)
    }
}