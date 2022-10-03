package com.weatherapplication.location

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.di.AppScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScoped
class LocationViewModel @Inject constructor(private val coLocation: CoLocation) : ViewModel() {

    private val _locationLiveData: MutableLiveData<Location> = MutableLiveData()
    val locationLiveData: LiveData<Location> get() = _locationLiveData

    fun getCurrentLocation() {
        viewModelScope.launch {
            coLocation.getLastLocation()?.run(_locationLiveData::postValue)
        }
    }
}