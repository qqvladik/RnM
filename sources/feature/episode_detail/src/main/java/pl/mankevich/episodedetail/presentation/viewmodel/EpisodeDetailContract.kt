package pl.mankevich.episodedetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias EpisodeDetailStateWithEffects = StateWithEffects<EpisodeDetailState, EpisodeDetailSideEffect>
typealias EpisodeDetailMviViewModel = MviViewModel<EpisodeDetailIntent, EpisodeDetailStateWithEffects>

sealed interface EpisodeDetailIntent {

    data object LoadEpisodeDetail : EpisodeDetailIntent, UniqueIntent

    data object LoadCharacters : EpisodeDetailIntent, UniqueIntent

    data class SeasonFilterClick(val season: Int) : EpisodeDetailIntent

    data class EpisodeFilterClick(val episode: Int) : EpisodeDetailIntent

    data class CharacterItemClick(val characterId: Int) : EpisodeDetailIntent

    data object BackClick : EpisodeDetailIntent
}

sealed interface EpisodeDetailSideEffect {

    data class NavigateToEpisodesListBySeason(val season: Int) : EpisodeDetailSideEffect

    data class NavigateToEpisodesListByEpisode(val episode: Int) : EpisodeDetailSideEffect

    data class NavigateToCharacterDetail(val characterId: Int) : EpisodeDetailSideEffect

    data object NavigateBack : EpisodeDetailSideEffect
}

data class EpisodeDetailState(
    val episodeError: Throwable? = null,
    val charactersError: Throwable? = null,
    val episode: Episode? = null,
    val characters: List<Character>? = null,
)