package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Location

interface LoadLocationDetailUseCase {

    operator fun invoke(locationId: Int): Flow<Location>
}