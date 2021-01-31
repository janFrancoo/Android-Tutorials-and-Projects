package com.janfranco.data

import com.janfranco.domain.Location

interface LocationPersistentSource {
    fun getPersistedLocations(): List<Location>
    fun saveNewLocation(location: Location)
}
