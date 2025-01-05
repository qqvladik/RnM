package pl.mankevich.data.paging.location

import dagger.assisted.AssistedFactory
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter
import javax.inject.Inject

class LocationPagingSourceCreator @Inject constructor(
    private val locationPagingSourceCreatorOnline: LocationPagingSourceCreatorOnline,
    private val locationPagingSourceCreatorOffline: LocationPagingSourceCreatorOffline
) {

    fun create(isOnline: Boolean, locationFilter: LocationFilter): PagingSource<Location> {
        return if (isOnline) {
            locationPagingSourceCreatorOnline.create(locationFilter)
        } else {
            locationPagingSourceCreatorOffline.create(locationFilter)
        }
    }
}

@AssistedFactory
interface LocationPagingSourceCreatorOffline {

    fun create(locationFilter: LocationFilter): LocationPagingSourceOffline
}

@AssistedFactory
interface LocationPagingSourceCreatorOnline {

    fun create(locationFilter: LocationFilter): LocationPagingSourceOnline
}