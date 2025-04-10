package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.domainapi.result.LocationDetailResult

interface LoadLocationDetailUseCase {

    operator fun invoke(locationId: Int): Flow<LocationDetailResult>
}