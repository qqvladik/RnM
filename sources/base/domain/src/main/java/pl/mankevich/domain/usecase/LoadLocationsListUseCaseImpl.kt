package pl.mankevich.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.domainapi.usecase.LoadLocationsListUseCase
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter
import javax.inject.Inject

class LoadLocationsListUseCaseImpl
@Inject constructor(
    private val locationRepository: LocationRepository
) : LoadLocationsListUseCase {

    override suspend operator fun invoke(locationFilter: LocationFilter): Flow<PagingData<Location>> {
        return locationRepository.getLocationsPageFlow(locationFilter)
    }
}