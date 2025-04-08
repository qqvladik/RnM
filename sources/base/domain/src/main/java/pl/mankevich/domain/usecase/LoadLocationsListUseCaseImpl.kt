package pl.mankevich.domain.usecase

import pl.mankevich.dataapi.dto.LocationsResultDto
import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.domainapi.result.LocationsResult
import pl.mankevich.domainapi.usecase.LoadLocationsListUseCase
import pl.mankevich.model.LocationFilter
import javax.inject.Inject

class LoadLocationsListUseCaseImpl
@Inject constructor(
    private val locationRepository: LocationRepository
) : LoadLocationsListUseCase {

    override suspend operator fun invoke(locationFilter: LocationFilter): LocationsResult {
        return locationRepository.getLocationsPageFlow(locationFilter).mapToResult()
    }
}

fun LocationsResultDto.mapToResult() = LocationsResult(
    isOnline = isOnline,
    locations = locations
)