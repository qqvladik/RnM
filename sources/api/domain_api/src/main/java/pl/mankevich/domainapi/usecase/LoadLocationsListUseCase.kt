package pl.mankevich.domainapi.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

interface LoadLocationsListUseCase {

    suspend operator fun invoke(locationFilter: LocationFilter): Flow<PagingData<Location>>
}