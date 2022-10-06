package com.weatherapplication.data

import com.weatherapplication.data.model.forecast.Forecast
import com.weatherapplication.data.model.weather.Weather

interface WeatherRepository {
    suspend fun fetchCurrentCityWeather(params: Map<String, String>): Weather
    suspend fun fetchDailyForecast(params: Map<String, String>): Forecast
}