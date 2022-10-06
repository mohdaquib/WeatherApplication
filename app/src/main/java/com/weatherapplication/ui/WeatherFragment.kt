package com.weatherapplication.ui

import android.os.Build
import android.os.Bundle
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
                    _binding?.loadingBar?.visibility = View.VISIBLE
                }
                is WeatherViewState.Success -> {
                    _binding?.loadingBar?.visibility = View.GONE
                    _binding?.temperature?.text = "${state.weather?.main?.temp.toString()}\u00B0"
                    if (!state.city.isNullOrEmpty()) {
                        _binding?.city?.text = state.city
                    }
                    state.forecast?.let {
                        ForecastBottomDialogFragment().apply {
                            val bundle = Bundle()
                            bundle.putParcelableArrayList("forecast_list", ArrayList(it.list))
                            arguments = bundle
                            isCancelable = false
                        }.show(childFragmentManager, "forecastSheetFragment")
                    }
                }
                is WeatherViewState.Error -> {
                    _binding?.loadingBar?.visibility = View.GONE
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
                ) || permissions.getOrDefault(
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}