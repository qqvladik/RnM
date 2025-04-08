package pl.mankevich.domainapi.usecase

import pl.mankevich.domainapi.result.LocationsResult
import pl.mankevich.model.LocationFilter

interface LoadLocationsListUseCase {

    suspend operator fun invoke(locationFilter: LocationFilter): LocationsResult
}