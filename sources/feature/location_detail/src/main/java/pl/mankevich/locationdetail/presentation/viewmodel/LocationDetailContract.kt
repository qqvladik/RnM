package pl.mankevich.locationdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Location

typealias LocationDetailStateWithEffects = StateWithEffects<LocationDetailState, LocationDetailSideEffect>
typealias LocationDetailMviViewModel = MviViewModel<LocationDetailIntent, LocationDetailStateWithEffects>

sealed class LocationDetailIntent {

    data object LoadLocation : LocationDetailIntent(), UniqueIntent

    data object LoadCharacters : LocationDetailIntent(), UniqueIntent

    data class CharacterItemClick(val characterId: Int) : LocationDetailIntent()
}

sealed interface LocationDetailSideEffect {

    data object OnLoadLocationRequested : LocationDetailSideEffect

    data object OnLoadLocationsRequested : LocationDetailSideEffect

    data class OnCharacterItemClicked(val characterId: Int) : LocationDetailSideEffect
}

data class LocationDetailState(
    val error: Throwable? = null,
    val location: Location? = null,
    val characters: List<Character>? = null,
)