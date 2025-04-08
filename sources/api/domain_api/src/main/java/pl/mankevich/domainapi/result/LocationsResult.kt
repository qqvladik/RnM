package pl.mankevich.domainapi.result

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Location

data class LocationsResult(
    val isOnline: Boolean,
    val locations: Flow<PagingData<Location>>
)
