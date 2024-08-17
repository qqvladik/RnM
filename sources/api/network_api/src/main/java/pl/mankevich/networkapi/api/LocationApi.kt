package pl.mankevich.networkapi.api

import pl.mankevich.networkapi.dto.LocationResponseDto
import pl.mankevich.networkapi.dto.LocationsListResponseDto

interface LocationApi {

    suspend fun getLocationById(id: Int): LocationResponseDto

    suspend fun getLocationsList(): LocationsListResponseDto
}