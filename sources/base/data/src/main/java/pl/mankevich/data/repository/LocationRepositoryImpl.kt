package pl.mankevich.data.repository

import android.util.Log
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.mankevich.data.mapper.mapToLocation
import pl.mankevich.data.mapper.mapToLocationDto
import pl.mankevich.data.paging.location.LocationPagingSourceCreator
import pl.mankevich.data.paging.location.LocationRemoteMediatorCreator
import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter
import pl.mankevich.remoteapi.api.LocationApi
import java.net.UnknownHostException
import javax.inject.Inject

class LocationRepositoryImpl
@Inject constructor(
    private val locationApi: LocationApi,
    private val locationDao: LocationDao,
    private val locationPagingSourceCreator: LocationPagingSourceCreator,
    private val locationRemoteMediatorCreator: LocationRemoteMediatorCreator,
    private val networkManager: NetworkManager
) : LocationRepository {

    private lateinit var onTableUpdateListener: () -> Unit

    override suspend fun getLocationsPageFlow(locationFilter: LocationFilter): Flow<PagingData<Location>> {
        val isOnline = networkManager.isOnline()
        val locationPagingSourceFactory = createPagingSourceFactory {
            locationPagingSourceCreator.create(isOnline, locationFilter)
        }

        val pager = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE * 2,
                maxSize = 200
            ),
            remoteMediator = locationRemoteMediatorCreator.create(isOnline, locationFilter),
            pagingSourceFactory = locationPagingSourceFactory,
        )

        return pager.flow.flowOn(Dispatchers.IO)
    }

    override fun getLocationDetail(locationId: Int): Flow<Location> =
        flow {
            emit(Unit)

            try {
                val locationResponse = locationApi.fetchLocationById(locationId)
                locationDao.insertLocation(locationResponse.mapToLocationDto())
            } catch (e: UnknownHostException) {
                Log.w("LocationRepositoryImpl", "getLocationDetail: $e")
            }
        }.flatMapLatest {
            locationDao.getLocationById(locationId)
                .distinctUntilChanged()
                .filterNotNull()
                .map {
                    it.mapToLocation()
                }
        }

    private fun createPagingSourceFactory(
        pagingSourceFactory: () -> PagingSource<Int, Location>
    ): PagingSourceFactory<Int, Location> {
        val invalidatingPagingSourceFactory = InvalidatingPagingSourceFactory(pagingSourceFactory)

        onTableUpdateListener = { invalidatingPagingSourceFactory.invalidate() }
        locationDao.addTableUpdateWeakListener(onTableUpdateListener)

        return invalidatingPagingSourceFactory
    }
}