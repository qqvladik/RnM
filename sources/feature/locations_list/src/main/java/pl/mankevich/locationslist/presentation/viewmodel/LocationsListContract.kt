package pl.mankevich.locationslist.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

typealias LocationsListStateWithEffects = StateWithEffects<LocationsListState, LocationsListSideEffect>
typealias LocationsListMviViewModel = MviViewModel<LocationsListIntent, LocationsListStateWithEffects>

sealed class LocationsListIntent {

    data class LoadLocations(val locationFilter: LocationFilter) : LocationsListIntent(), UniqueIntent

    data class Refresh(val locationFilter: LocationFilter) : LocationsListIntent(), UniqueIntent

    data class CharacterItemClick(val locationId: Int) : LocationsListIntent()

    data class NameChanged(val name: String) : LocationsListIntent()

    data class TypeChanged(val type: String) : LocationsListIntent()

    data class DimensionChanged(val dimension: String) : LocationsListIntent()
}

sealed interface LocationsListSideEffect {

    data class OnCharacterItemClicked(val characterId: Int) : LocationsListSideEffect

    data class OnLoadLocationsRequested(val locationFilter: LocationFilter) :
        LocationsListSideEffect

    data class OnRefreshRequested(val locationFilter: LocationFilter) : LocationsListSideEffect
}

@Immutable
data class LocationsListState(
    val locationFilter: LocationFilter = LocationFilter(),
    // Flow is unstable, so it will always recompose https://issuetracker.google.com/issues/183495984
    // Currently there is no solution how to fit Paging in Unidirectional data flow (MVI)
    val locations: Flow<PagingData<Location>> = emptyFlow(),
    val typeLabelList: List<String> = listOf("Planet", "Space station"),
    val dimensionLabelList: List<String> = listOf("Dimension C-137", "Replacement Dimension"),
)