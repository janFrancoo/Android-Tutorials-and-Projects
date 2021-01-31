package com.janfranco.usecases

import com.janfranco.data.LocationsRepository
import com.janfranco.domain.Location

class RequestNewLocation(
    private val locationsRepository: LocationsRepository
) {
    operator fun invoke(): List<Location> = locationsRepository.requestNewLocation()
}
