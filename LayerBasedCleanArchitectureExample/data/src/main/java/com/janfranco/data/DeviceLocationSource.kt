package com.janfranco.data

import com.janfranco.domain.Location

interface DeviceLocationSource {
    fun getDeviceLocation(): Location
}
