package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.dataapi.dto.LocationDetailResultDto
import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.domainapi.result.LocationDetailResult
import pl.mankevich.domainapi.usecase.LoadLocationDetailUseCase
import javax.inject.Inject

class LoadLocationDetailUseCaseImpl
@Inject constructor(
    private val locationRepository: LocationRepository
) : LoadLocationDetailUseCase {

    override operator fun invoke(locationId: Int): Flow<LocationDetailResult> =
        locationRepository.getLocationDetail(locationId).map { it.mapToResult() }
}

fun LocationDetailResultDto.mapToResult(): LocationDetailResult =
    LocationDetailResult(
        isOnline = isOnline,
        location = location
    )