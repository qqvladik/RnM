package pl.mankevich.locationslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.coreui.mvi.SideEffects
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadLocationsListUseCase
import pl.mankevich.locationslist.getLocationFilter
import pl.mankevich.model.LocationFilter

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class LocationsListViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadLocationsListUseCase: LoadLocationsListUseCase,
) : LocationsListMviViewModel(
    initialStateWithEffects = LocationsListStateWithEffects(
        state = LocationsListState(),
        sideEffects = SideEffects<LocationsListSideEffect>().add(
            LocationsListSideEffect.OnInitRequested(
                savedStateHandle.getLocationFilter()
            )
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<LocationsListViewModel>

    override fun executeIntent(intent: LocationsListIntent): Flow<Transform<LocationsListStateWithEffects>> =
        when (intent) {
            is LocationsListIntent.Init -> flowOf(
                LocationsListTransforms.Init(intent.locationFilter)
            )

            is LocationsListIntent.LoadLocations -> loadLocations(
                instantRefresh = false,
                intent.locationFilter
            )

            is LocationsListIntent.Refresh -> loadLocations(
                instantRefresh = true,
                intent.locationFilter
            )

            is LocationsListIntent.NameChanged -> flowOf(
                LocationsListTransforms.ChangeName(name = intent.name)
            )

            is LocationsListIntent.TypeChanged -> flowOf(
                LocationsListTransforms.ChangeType(type = intent.type)
            )

            is LocationsListIntent.DimensionChanged -> flowOf(
                LocationsListTransforms.ChangeDimension(dimension = intent.dimension)
            )

            is LocationsListIntent.LocationItemClick -> flowOf(
                LocationsListTransforms.LocationItemClick(intent.locationId)
            )
        }

    private fun loadLocations(
        instantRefresh: Boolean,
        locationFilter: LocationFilter
    ): Flow<Transform<LocationsListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
        emit(
            LocationsListTransforms.LoadLocationsListSuccess(
                loadLocationsListUseCase(locationFilter).cachedIn(viewModelScope)
            )
        )
    }

    fun handleSideEffect(
        sideEffect: LocationsListSideEffect,
        onLocationItemClick: (Int) -> Unit
    ) {
        when (sideEffect) {
            is LocationsListSideEffect.OnInitRequested ->
                sendIntent(LocationsListIntent.Init(sideEffect.locationFilter))

            is LocationsListSideEffect.OnCharacterItemClicked ->
                onLocationItemClick(sideEffect.characterId)

            is LocationsListSideEffect.OnLoadLocationsRequested ->
                sendIntent(LocationsListIntent.LoadLocations(sideEffect.locationFilter))

            is LocationsListSideEffect.OnRefreshRequested ->
                sendIntent(LocationsListIntent.Refresh(sideEffect.locationFilter))
        }
    }
}