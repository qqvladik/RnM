package pl.mankevich.networkapi.datasource

import pl.mankevich.networkapi.dto.LocationResponseDto

interface LocationNetworkDataSource {

    suspend fun getLocationById(id: Int): LocationResponseDto

    suspend fun getLocationsList(): List<LocationResponseDto>
}