package pl.mankevich.locationslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<LocationsListViewModel>

    private val currentState: LocationsListState
        get() = stateWithEffects.value.state

    override fun initialize() {
        sendIntent(LocationsListIntent.Init(savedStateHandle.getLocationFilter()))
    }

    override fun executeIntent(intent: LocationsListIntent): Flow<Transform<LocationsListStateWithEffects>> =
        when (intent) {
            is LocationsListIntent.Init -> flow {
                emit(LocationsListTransforms.Init(intent.locationFilter))
                emitAll(loadLocations(intent.locationFilter, instantRefresh = true))
            }

            LocationsListIntent.Refresh -> loadLocations(
                currentState.locationFilter,
                instantRefresh = true
            )

            is LocationsListIntent.NameChanged -> flow {
                emit(LocationsListTransforms.ChangeName(name = intent.name))
                val newFilter = currentState.locationFilter.copy(name = intent.name)
                emitAll(loadLocations(newFilter))
            }

            is LocationsListIntent.TypeChanged -> flow {
                emit(LocationsListTransforms.ChangeType(type = intent.type))
                val newFilter = currentState.locationFilter.copy(type = intent.type)
                emitAll(loadLocations(newFilter))
            }

            is LocationsListIntent.DimensionChanged -> flow {
                emit(LocationsListTransforms.ChangeDimension(dimension = intent.dimension))
                val newFilter = currentState.locationFilter.copy(dimension = intent.dimension)
                emitAll(loadLocations(newFilter))
            }

            is LocationsListIntent.LocationItemClick -> flowOf(
                LocationsListTransforms.LocationItemClick(intent.locationId)
            )

            LocationsListIntent.BackClick -> flowOf(LocationsListTransforms.BackClick)
        }

    private fun loadLocations(
        locationFilter: LocationFilter,
        instantRefresh: Boolean = false
    ): Flow<Transform<LocationsListStateWithEffects>> =
        flow {
            if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
            emit(
                LocationsListTransforms.LoadLocationsListSuccess(
                    loadLocationsListUseCase(locationFilter).cachedIn(viewModelScope)
                )
            )
        }
}