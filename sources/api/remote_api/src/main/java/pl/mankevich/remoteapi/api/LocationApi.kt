package pl.mankevich.remoteapi.api

import pl.mankevich.remoteapi.response.LocationResponse
import pl.mankevich.remoteapi.response.LocationsListResponse

interface LocationApi {

    suspend fun getLocationById(id: Int): LocationResponse

    suspend fun getLocationsList(): LocationsListResponse
}