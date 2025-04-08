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

sealed interface LocationsListIntent {

    data class Init(val locationFilter: LocationFilter) : LocationsListIntent, UniqueIntent

    data object Refresh : LocationsListIntent, UniqueIntent

    data class NameChanged(val name: String) : LocationsListIntent, UniqueIntent

    data class TypeChanged(val type: String) : LocationsListIntent, UniqueIntent

    data class DimensionChanged(val dimension: String) : LocationsListIntent, UniqueIntent

    data class LocationItemClick(val locationId: Int) : LocationsListIntent

    data object BackClick : LocationsListIntent
}

sealed interface LocationsListSideEffect {

    data class NavigateToLocationDetail(val locationId: Int) : LocationsListSideEffect

    data object NavigateBack : LocationsListSideEffect
}

@Immutable
data class LocationsListState(
    val locationFilter: LocationFilter = LocationFilter(),
    val isOnline: Boolean = false,
    // Flow is unstable, so it will always recompose https://issuetracker.google.com/issues/183495984
    // Currently there is no solution how to fit Paging in Unidirectional data flow (MVI)
    val locations: Flow<PagingData<Location>> = emptyFlow(),
    val typeLabelList: List<String> = listOf("Planet", "Space station"),
    val dimensionLabelList: List<String> = listOf("Dimension C-137", "Replacement Dimension"),
)