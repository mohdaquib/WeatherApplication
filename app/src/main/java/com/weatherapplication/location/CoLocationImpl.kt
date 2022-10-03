package com.weatherapplication.location

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CoLocationImpl(private val context: Context) : CoLocation {
    private val locationProvider: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun isLocationAvailable(): Boolean =
        locationProvider.locationAvailability.await().isLocationAvailable

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCurrentLocation(priority: Int): Location? =
        suspendCancellableCoroutine { cont ->
            val cancellableTokenSource = CancellationTokenSource()
            locationProvider.getCurrentLocation(priority, cancellableTokenSource.token).apply {
                addOnSuccessListener { cont.resume(it) }
                addOnCanceledListener { cont.resume(null) }
                addOnFailureListener { cont.resumeWithException(it) }
            }
            cont.invokeOnCancellation { cancellableTokenSource.cancel() }
        }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getLastLocation(): Location? = locationProvider.lastLocation.await()
}