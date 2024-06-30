package pl.mankevich.network.datasource

import pl.mankevich.network.api.RnmApi
import pl.mankevich.network.api.response.LocationResponse
import pl.mankevich.networkapi.datasource.LocationNetworkDataSource
import pl.mankevich.networkapi.dto.LocationResponseDto
import javax.inject.Inject

class LocationNetworkDataSourceImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : LocationNetworkDataSource {

    override suspend fun getLocationById(id: Int): LocationResponseDto =
        rnmApi.fetchSingleLocation(id).mapToLocationDto()

    override suspend fun getLocationsList(): List<LocationResponseDto> =
        rnmApi.fetchAllLocations().locationsResponse.map { it.mapToLocationDto() }
}

private fun LocationResponse.mapToLocationDto() = LocationResponseDto(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentIds = residents.map { it.obtainId() }
)
