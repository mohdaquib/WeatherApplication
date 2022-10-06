package com.weatherapplication.data.model.forecast

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItem>,
    val message: Double
)