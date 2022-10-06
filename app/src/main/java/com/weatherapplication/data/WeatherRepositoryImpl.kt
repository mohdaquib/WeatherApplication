package com.weatherapplication.data

import com.weatherapplication.data.model.forecast.Forecast
import com.weatherapplication.data.model.weather.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService) :
    WeatherRepository {
    override suspend fun fetchCurrentCityWeather(params: Map<String, String>): Weather {
        return weatherApiService.fetchCityCurrentWeather(params)
    }

    override suspend fun fetchDailyForecast(params: Map<String, String>): Forecast {
        return weatherApiService.fetchDailyForecast(params)
    }
}