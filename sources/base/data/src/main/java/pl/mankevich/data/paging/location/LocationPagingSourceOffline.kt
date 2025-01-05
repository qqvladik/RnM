package pl.mankevich.data.paging.location

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.data.mapper.mapToEntity
import pl.mankevich.data.mapper.mapToLocation
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

class LocationPagingSourceOffline @AssistedInject constructor(
    private val locationDao: LocationDao,
    private val transaction: Transaction,
    @Assisted private val locationFilter: LocationFilter,
) : PagingSource<Location>() {

    override suspend fun getCount(): Int {
        return locationDao.getLocationsCount(locationFilter.mapToEntity())
    }

    override suspend fun getData(limit: Int, offset: Int): List<Location> {
        return locationDao.getLocationsList(
            locationFilter.mapToEntity(),
            limit,
            offset
        ).map { it.mapToLocation() }
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        transaction(block)
}
