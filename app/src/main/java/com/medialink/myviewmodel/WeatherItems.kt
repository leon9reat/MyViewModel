package com.medialink.myviewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WeatherItems (
    var id: Int = 0,
    var name: String? = null,
    var currentWeather: String? = null,
    var description: String? = null,
    var temperature: String? = null
): Parcelable