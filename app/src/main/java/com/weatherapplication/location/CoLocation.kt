package com.weatherapplication.location

import android.content.Context
import android.location.Location

interface CoLocation {
    companion object {
        fun from(context: Context): CoLocation = CoLocationImpl(context.applicationContext)
    }

    suspend fun isLocationAvailable(): Boolean

    suspend fun getCurrentLocation(priority: Int): Location?

    suspend fun getLastLocation(): Location?
}