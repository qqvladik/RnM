package pl.mankevich.dataapi.dto

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Location

data class LocationsResultDto(
    val isOnline: Boolean,
    val locations: Flow<PagingData<Location>>
)
