package pl.mankevich.remoteretrofit.api

import pl.mankevich.remoteretrofit.retrofit.RnmApi
import pl.mankevich.remoteretrofit.retrofit.response.LocationRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.LocationsListRetrofitResponse
import pl.mankevich.remoteapi.api.LocationApi
import pl.mankevich.remoteapi.response.LocationResponse
import pl.mankevich.remoteapi.response.LocationsListResponse
import javax.inject.Inject

class LocationApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : LocationApi {

    override suspend fun getLocationById(id: Int): LocationResponse =
        rnmApi.fetchSingleLocation(id).mapToLocationDto()

    override suspend fun getLocationsList(): LocationsListResponse =
        rnmApi.fetchAllLocations().mapToLocationsListResponseDto()
}

private fun LocationsListRetrofitResponse.mapToLocationsListResponseDto() = LocationsListResponse(
    info = info.mapToInfoResponseDto(),
    locationsResponse = locationsResponse.map { it.mapToLocationDto() }
)

private fun LocationRetrofitResponse.mapToLocationDto() = LocationResponse(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentIds = residents.map { it.obtainId()!! }
)
