package com.weatherapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.data.WeatherRepository
import com.weatherapplication.data.model.Weather
import com.weatherapplication.di.AppScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScoped
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    private val _weatherViewStateLiveData: MutableLiveData<WeatherViewState> =
        MutableLiveData<WeatherViewState>()
    val weatherViewState: LiveData<WeatherViewState> get() = _weatherViewStateLiveData

    fun fetchWeather(lat: Double, lon: Double, apiKey: String) {
        _weatherViewStateLiveData.value = WeatherViewState.Loading
        viewModelScope.launch {
            try {
                val weather =
                    weatherRepository.fetchCurrentCityWeather(requestParams(lat, lon, apiKey))
                _weatherViewStateLiveData.value = WeatherViewState.Success(weather)
            } catch (e: Exception) {
                _weatherViewStateLiveData.value = WeatherViewState.Error
            }
        }
    }

    private fun requestParams(lat: Double, lon: Double, apiKey: String): Map<String, String> {
        return mapOf(
            "lat" to lat.toString(),
            "lon" to lon.toString(),
            "appid" to apiKey,
            "units" to "metric"
        )
    }
}

sealed class WeatherViewState {
    object Loading : WeatherViewState()
    object Error : WeatherViewState()
    data class Success(val weather: Weather) : WeatherViewState()
}