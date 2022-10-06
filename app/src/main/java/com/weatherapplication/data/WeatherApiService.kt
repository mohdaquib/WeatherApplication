package com.weatherapplication.data

import com.weatherapplication.data.model.forecast.Forecast
import com.weatherapplication.data.model.weather.Weather
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApiService {
    @GET("weather")
    suspend fun fetchCityCurrentWeather(@QueryMap params: Map<String, String>): Weather

    @GET("forecast/daily")
    suspend fun fetchDailyForecast(@QueryMap params: Map<String, String>): Forecast
}