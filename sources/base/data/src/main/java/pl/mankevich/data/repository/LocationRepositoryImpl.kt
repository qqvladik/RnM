package pl.mankevich.data.repository

import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.databaseapi.dao.LocationDao
import javax.inject.Inject

class LocationRepositoryImpl
@Inject constructor(
    private val locationDao: LocationDao,
) : LocationRepository {


}