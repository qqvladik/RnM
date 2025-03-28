package pl.mankevich.characterdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailStateWithEffects = StateWithEffects<CharacterDetailState, CharacterDetailSideEffect>
typealias CharacterDetailMviViewModel = MviViewModel<CharacterDetailIntent, CharacterDetailStateWithEffects>

sealed class CharacterDetailIntent {

    data object LoadCharacterDetail : CharacterDetailIntent(), UniqueIntent

    data object LoadEpisodes : CharacterDetailIntent(), UniqueIntent

    data class StatusFilterClick(val status: String) : CharacterDetailIntent()

    data class SpeciesFilterClick(val species: String) : CharacterDetailIntent()

    data class GenderFilterClick(val gender: String) : CharacterDetailIntent()

    data class TypeFilterClick(val type: String) : CharacterDetailIntent()

    data class LocationItemClick(val locationId: Int) : CharacterDetailIntent()

    data class EpisodeItemClick(val episodeId: Int) : CharacterDetailIntent()

    data object BackClick : CharacterDetailIntent()
}

sealed interface CharacterDetailSideEffect {

    data class NavigateToCharactersListByStatus(val status: String) : CharacterDetailSideEffect

    data class NavigateToCharactersListBySpecies(val species: String) : CharacterDetailSideEffect

    data class NavigateToCharactersListByGender(val gender: String) : CharacterDetailSideEffect

    data class NavigateToCharactersListByType(val type: String) : CharacterDetailSideEffect

    data class NavigateToLocationDetail(val locationId: Int) : CharacterDetailSideEffect

    data class NavigateToEpisodeDetail(val episodeId: Int) : CharacterDetailSideEffect

    data object NavigateBack : CharacterDetailSideEffect
}

data class CharacterDetailState(
    val characterError: Throwable? = null,
    val episodesError: Throwable? = null,
    val character: Character? = null,
    val episodes: List<Episode>? = null,
)