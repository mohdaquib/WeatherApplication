package com.weatherapplication.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.weatherapplication.BuildConfig
import com.weatherapplication.R
import com.weatherapplication.appComp
import com.weatherapplication.databinding.FragmentWeatherBinding
import com.weatherapplication.location.LocationViewModel
import javax.inject.Inject

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private var _binding: FragmentWeatherBinding? = null
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.appComp?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherBinding.bind(view)
        weatherViewModel =
            ViewModelProvider(this, factory = viewModelFactory)[WeatherViewModel::class.java]
        locationViewModel =
            ViewModelProvider(this, factory = viewModelFactory)[LocationViewModel::class.java]
        weatherViewModel.weatherViewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WeatherViewState.Loading -> {

                }
                is WeatherViewState.Success -> {
                    Log.d("Weather", state.weather.toString())
                }
                is WeatherViewState.Error -> {

                }
            }
        }
        startLocationPermission()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun startLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) -> {
                    locationViewModel.getCurrentLocation()
                    locationViewModel.locationLiveData.observe(viewLifecycleOwner) { location ->
                        if (location != null) {
                            weatherViewModel.fetchWeather(
                                location.latitude,
                                location.longitude,
                                BuildConfig.API_KEY
                            )
                        }
                    }
                }
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    locationViewModel.getCurrentLocation()
                    locationViewModel.locationLiveData.observe(viewLifecycleOwner) { location ->
                        if (location != null) {
                            weatherViewModel.fetchWeather(
                                location.latitude,
                                location.longitude,
                                BuildConfig.API_KEY
                            )
                        }
                    }
                }
                else -> {
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}