package pl.mankevich.data.paging.location

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.data.mapper.mapToLocationDto
import pl.mankevich.data.mapper.mapToEntity
import pl.mankevich.data.mapper.mapToQuery
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.databaseapi.entity.LocationPageKeyEntity
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter
import pl.mankevich.remoteapi.api.LocationApi

class LocationRemoteMediator @AssistedInject constructor(
    private val transaction: Transaction,
    private val locationDao: LocationDao,
    private val relationsDao: RelationsDao,
    private val locationApi: LocationApi,
    @Assisted private val locationFilter: LocationFilter
) : RemoteMediator<Int, Location>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Location>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    1
                    // It works bad when remoteKey > 1. It scrolls to the top and loads PREPEND data
                    // infinitely because Room's PagingSource has such weird behavior
//                    val remoteKey = state.anchorPosition?.let { position ->
//                        state.closestItemToPosition(position)?.let {
//                            locationDao.getPageKey(it.id, filter.mapToFilterDto())
//                        }
//                    }
//                    remoteKey?.value ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = state.firstItemOrNull()?.let {
                        locationDao.getPageKey(it.id, locationFilter.mapToEntity())
                    }
                    remoteKey?.previousPageKey ?: return MediatorResult.Success(remoteKey != null)
                }

                LoadType.APPEND -> {
                    val remoteKey = state.lastItemOrNull()?.let {
                        locationDao.getPageKey(it.id, locationFilter.mapToEntity())
                    }
                    remoteKey?.nextPageKey ?: return MediatorResult.Success(remoteKey != null)
                }
            }

            val locationsListResponse = locationApi.fetchLocationsList(
                page = currentPage,
                filter = locationFilter.mapToQuery()
            )
            val responseInfo = locationsListResponse.info

            val idPageKeys = locationsListResponse.locationsResponse.map {
                LocationPageKeyEntity(
                    locationId = it.id,
                    filter = locationFilter.mapToEntity(),
                    value = currentPage,
                    previousPageKey = responseInfo.prev,
                    nextPageKey = responseInfo.next
                )
            }

            transaction {
                val locations = locationsListResponse.locationsResponse.map { locationResponse ->
                    relationsDao.insertLocationCharacters(locationResponse.id, locationResponse.residentIds)
                    locationResponse.mapToLocationDto()
                }

                locationDao.insertPageKeysList(idPageKeys)
                locationDao.insertLocationsList(locations)
            }
            MediatorResult.Success(endOfPaginationReached = responseInfo.next == null)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}