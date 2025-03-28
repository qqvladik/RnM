package pl.mankevich.locationdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Location

typealias LocationDetailStateWithEffects = StateWithEffects<LocationDetailState, LocationDetailSideEffect>
typealias LocationDetailMviViewModel = MviViewModel<LocationDetailIntent, LocationDetailStateWithEffects>

sealed interface LocationDetailIntent {

    data object LoadLocationDetail : LocationDetailIntent, UniqueIntent

    data object LoadCharacters : LocationDetailIntent, UniqueIntent

    data class DimensionFilterClick(val dimension: String) : LocationDetailIntent

    data class TypeFilterClick(val type: String) : LocationDetailIntent

    data class CharacterItemClick(val characterId: Int) : LocationDetailIntent

    data object BackClick : LocationDetailIntent
}

sealed interface LocationDetailSideEffect {

    data class NavigateToLocationsListByDimension(val dimension: String) : LocationDetailSideEffect

    data class NavigateToLocationsListByType(val type: String) : LocationDetailSideEffect

    data class NavigateToCharacterDetail(val characterId: Int) : LocationDetailSideEffect

    data object NavigateBack : LocationDetailSideEffect
}

data class LocationDetailState(
    val locationError: Throwable? = null,
    val charactersError: Throwable? = null,
    val location: Location? = null,
    val characters: List<Character>? = null,
)