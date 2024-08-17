package pl.mankevich.network.api

import pl.mankevich.network.retrofit.RnmApi
import pl.mankevich.network.retrofit.response.LocationResponse
import pl.mankevich.network.retrofit.response.LocationsListResponse
import pl.mankevich.networkapi.api.LocationApi
import pl.mankevich.networkapi.dto.LocationResponseDto
import pl.mankevich.networkapi.dto.LocationsListResponseDto
import javax.inject.Inject

class LocationApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : LocationApi {

    override suspend fun getLocationById(id: Int): LocationResponseDto =
        rnmApi.fetchSingleLocation(id).mapToLocationDto()

    override suspend fun getLocationsList(): LocationsListResponseDto =
        rnmApi.fetchAllLocations().mapToLocationsListResponseDto()
}

private fun LocationsListResponse.mapToLocationsListResponseDto() = LocationsListResponseDto(
    info = info.mapToInfoResponseDto(),
    locationsResponse = locationsResponse.map { it.mapToLocationDto() }
)

private fun LocationResponse.mapToLocationDto() = LocationResponseDto(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentIds = residents.map { it.obtainId()!! }
)
