package pl.mankevich.data.paging.location

import dagger.assisted.AssistedFactory
import pl.mankevich.model.LocationFilter
import javax.inject.Inject

class LocationRemoteMediatorCreator @Inject constructor(
    private val locationRemoteMediatorFactoryOnline: LocationRemoteMediatorFactoryOnline
) {

    fun create(isOnline: Boolean, locationFilter: LocationFilter): LocationRemoteMediator? {
        return if (isOnline) {
            locationRemoteMediatorFactoryOnline.create(locationFilter)
        } else {
            null
        }
    }
}

@AssistedFactory
interface LocationRemoteMediatorFactoryOnline {

    fun create(locationFilter: LocationFilter): LocationRemoteMediator
}