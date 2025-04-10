package pl.mankevich.data.repository

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.mankevich.core.di.Dispatcher
import pl.mankevich.core.di.RnmDispatchers.IO
import pl.mankevich.data.mapper.mapToLocation
import pl.mankevich.data.mapper.mapToLocationDto
import pl.mankevich.data.paging.location.LocationPagingSourceCreator
import pl.mankevich.data.paging.location.LocationRemoteMediatorCreator
import pl.mankevich.dataapi.dto.LocationDetailResultDto
import pl.mankevich.dataapi.dto.LocationsResultDto
import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter
import pl.mankevich.remoteapi.api.LocationApi
import javax.inject.Inject

class LocationRepositoryImpl
@Inject constructor(
    private val locationApi: LocationApi,
    private val locationDao: LocationDao,
    private val relationsDao: RelationsDao,
    private val locationPagingSourceCreator: LocationPagingSourceCreator,
    private val locationRemoteMediatorCreator: LocationRemoteMediatorCreator,
    private val networkManager: NetworkManager,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : LocationRepository {

    private lateinit var onTableUpdateListener: () -> Unit

    override suspend fun getLocationsPageFlow(locationFilter: LocationFilter): LocationsResultDto {
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

        return LocationsResultDto(
            isOnline = isOnline,
            locations = pager.flow.flowOn(dispatcher)
        )
    }

    override fun getLocationDetail(locationId: Int): Flow<LocationDetailResultDto> {
        val fetchFlow = flow {
            emit(null)

            val isOnline = networkManager.isOnline()
            if (isOnline) {
                val locationResponse = locationApi.fetchLocationById(locationId)
                locationDao.insertLocation(locationResponse.mapToLocationDto())
                relationsDao.insertLocationCharacters(locationId, locationResponse.residentIds)
            }

            emit(isOnline)
        }

        val loadFlow = locationDao.getLocationById(locationId)
            .distinctUntilChanged()
            .map {
                it?.mapToLocation()
            }

        return loadFlow.combine(fetchFlow) { location, isOnline ->
            LocationDetailResultDto(
                isOnline = isOnline,
                location = location,
            )
        }.flowOn(dispatcher)
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