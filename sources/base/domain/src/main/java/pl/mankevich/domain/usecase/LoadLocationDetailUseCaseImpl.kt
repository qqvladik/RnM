package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.LocationRepository
import pl.mankevich.domainapi.usecase.LoadLocationDetailUseCase
import pl.mankevich.model.Location
import javax.inject.Inject

class LoadLocationDetailUseCaseImpl
@Inject constructor(
    private val locationRepository: LocationRepository
) : LoadLocationDetailUseCase {

    override operator fun invoke(locationId: Int): Flow<Location> =
        locationRepository.getLocationDetail(locationId)
}