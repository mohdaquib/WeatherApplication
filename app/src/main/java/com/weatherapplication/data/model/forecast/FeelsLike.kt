package com.weatherapplication.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
): Parcelable