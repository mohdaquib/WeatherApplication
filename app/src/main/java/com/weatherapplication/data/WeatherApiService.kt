package com.weatherapplication.data

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApiService {
    @GET("current")
    suspend fun fetchCityCurrentWeather(@QueryMap params: Map<String, String>): Weather
}