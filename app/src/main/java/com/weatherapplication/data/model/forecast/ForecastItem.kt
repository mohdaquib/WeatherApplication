package com.weatherapplication.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastItem(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    val feels_like: FeelsLike,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<Weather>
): Parcelable
