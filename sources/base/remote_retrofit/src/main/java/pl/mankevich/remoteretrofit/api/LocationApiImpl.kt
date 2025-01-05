package pl.mankevich.remoteretrofit.api

import pl.mankevich.remoteapi.api.LocationApi
import pl.mankevich.remoteapi.query.LocationFilterQuery
import pl.mankevich.remoteapi.response.InfoResponse
import pl.mankevich.remoteapi.response.LocationResponse
import pl.mankevich.remoteapi.response.LocationsListResponse
import pl.mankevich.remoteretrofit.retrofit.RnmApi
import pl.mankevich.remoteretrofit.retrofit.response.LocationRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.LocationsListRetrofitResponse
import javax.inject.Inject

class LocationApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : LocationApi {

    override suspend fun fetchLocationById(id: Int): LocationResponse =
        rnmApi.fetchSingleLocation(id).mapToLocationDto()

    override suspend fun fetchLocationsList(
        page: Int,
        filter: LocationFilterQuery
    ): LocationsListResponse = try {
        rnmApi.fetchAllLocations(
            page = page,
            name = filter.name,
            type = filter.type,
            dimension = filter.dimension
        ).mapToLocationsListResponseDto()
    } catch (e: retrofit2.HttpException) {
        if (e.code() == 404) {
            LocationsListResponse(
                info = InfoResponse(
                    count = 0,
                    pages = 0,
                    next = null,
                    prev = null
                ),
                locationsResponse = emptyList()
            )
        } else {
            e.printStackTrace()
            throw e
        }
    }

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
