package pl.mankevich.networkapi.datasource

import pl.mankevich.networkapi.dto.LocationResponseDto
import pl.mankevich.networkapi.dto.LocationsListResponseDto

interface LocationNetworkDataSource {

    suspend fun getLocationById(id: Int): LocationResponseDto

    suspend fun getLocationsList(): LocationsListResponseDto
}