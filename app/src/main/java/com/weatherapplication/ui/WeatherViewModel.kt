package com.weatherapplication.ui

import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.data.WeatherRepository
import com.weatherapplication.data.model.forecast.Forecast
import com.weatherapplication.data.model.weather.Weather
import com.weatherapplication.di.AppScoped
import kotlinx.coroutines.*
import javax.inject.Inject

@AppScoped
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val geocoder: Geocoder,
    private val ioDispatcher: CoroutineDispatcher
) :
    ViewModel() {
    private val _weatherViewStateLiveData: MutableLiveData<WeatherViewState> =
        MutableLiveData<WeatherViewState>()
    val weatherViewState: LiveData<WeatherViewState> get() = _weatherViewStateLiveData

    fun fetchWeather(lat: Double, lon: Double, apiKey: String) {
        _weatherViewStateLiveData.value = WeatherViewState.Loading
        viewModelScope.launch {
            try {
                val weather: Deferred<Weather> = async {
                    val params = requestParams(lat, lon, apiKey).toMutableMap()
                    params["units"] = "metric"
                    weatherRepository.fetchCurrentCityWeather(params)
                }
                val city: Deferred<String?> = async { getCityName(lat, lon) }
                val forecast: Deferred<Forecast> = async {
                    val params = requestParams(lat, lon, apiKey).toMutableMap()
                    params["cnt"] = "4"
                    params["units"] = "metric"
                    weatherRepository.fetchDailyForecast(params)
                }
                _weatherViewStateLiveData.value =
                    WeatherViewState.Success(weather.await(), city.await(), forecast.await())
            } catch (e: Exception) {
                _weatherViewStateLiveData.value = WeatherViewState.Error
            }
        }
    }

    private fun requestParams(lat: Double, lon: Double, apiKey: String): Map<String, String> {
        return mapOf(
            "lat" to lat.toString(),
            "lon" to lon.toString(),
            "appid" to apiKey
        )
    }

    private suspend fun getCityName(lat: Double, lon: Double): String? {
        var city: String? = null
        withContext(ioDispatcher) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                val address = geocoder.getFromLocation(lat, lon, 3)
                city = address?.get(0)?.adminArea
            } else {
                geocoder.getFromLocation(lat, lon, 3) { address ->
                    city = address[0].adminArea
                }
            }
        }
        return city
    }
}

sealed class WeatherViewState {
    object Loading : WeatherViewState()
    object Error : WeatherViewState()
    data class Success(val weather: Weather?, val city: String?, val forecast: Forecast?) : WeatherViewState()
}