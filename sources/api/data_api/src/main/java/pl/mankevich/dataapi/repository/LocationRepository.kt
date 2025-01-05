package pl.mankevich.dataapi.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

interface LocationRepository {

    suspend fun getLocationsPageFlow(locationFilter: LocationFilter): Flow<PagingData<Location>>

    fun getLocationDetail(locationId: Int): Flow<Location>
}