package com.janfranco.data

import com.janfranco.domain.Location

class LocationsRepository(
    private val locationPersistentSource: LocationPersistentSource,
    private val deviceLocationSource: DeviceLocationSource
) {

    fun getSavedLocations(): List<Location> = locationPersistentSource.getPersistedLocations()

    fun requestNewLocation(): List<Location> {
        val newLocation = deviceLocationSource.getDeviceLocation()
        locationPersistentSource.saveNewLocation(newLocation)
        return getSavedLocations()
    }

}
