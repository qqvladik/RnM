package pl.mankevich.dataapi.repository

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.dto.LocationDetailResultDto
import pl.mankevich.dataapi.dto.LocationsResultDto
import pl.mankevich.model.LocationFilter

interface LocationRepository {

    suspend fun getLocationsPageFlow(locationFilter: LocationFilter): LocationsResultDto

    fun getLocationDetail(locationId: Int): Flow<LocationDetailResultDto>
}