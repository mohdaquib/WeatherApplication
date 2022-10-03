package com.weatherapplication.data

import com.weatherapplication.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApiService {
    @GET("weather")
    suspend fun fetchCityCurrentWeather(@QueryMap params: Map<String, String>): Weather
}