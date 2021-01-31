package com.janfranco.cleanarchitectureexample.framework

import com.janfranco.data.LocationPersistentSource
import com.janfranco.domain.Location

class InMemoryLocationPersistenceSource: LocationPersistentSource {
    private var locations: List<Location> = emptyList()

    override fun getPersistedLocations(): List<Location> = locations

    override fun saveNewLocation(location: Location) {
        locations = locations + location
    }
}
