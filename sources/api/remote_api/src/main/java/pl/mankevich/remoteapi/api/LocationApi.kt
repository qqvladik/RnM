package pl.mankevich.remoteapi.api

import pl.mankevich.remoteapi.query.LocationFilterQuery
import pl.mankevich.remoteapi.response.LocationResponse
import pl.mankevich.remoteapi.response.LocationsListResponse

interface LocationApi {

    suspend fun fetchLocationById(id: Int): LocationResponse

    suspend fun fetchLocationsList(page: Int, filter: LocationFilterQuery): LocationsListResponse
}