package pl.mankevich.episodedetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias EpisodeDetailStateWithEffects = StateWithEffects<EpisodeDetailState, EpisodeDetailSideEffect>
typealias EpisodeDetailMviViewModel = MviViewModel<EpisodeDetailIntent, EpisodeDetailStateWithEffects>

sealed class EpisodeDetailIntent {

    data object LoadEpisode : EpisodeDetailIntent(), UniqueIntent

    data object LoadCharacters : EpisodeDetailIntent(), UniqueIntent

    data class CharacterItemClick(val characterId: Int) : EpisodeDetailIntent()
}

sealed interface EpisodeDetailSideEffect {

    data object OnLoadEpisodeRequested : EpisodeDetailSideEffect

    data object OnLoadEpisodesRequested : EpisodeDetailSideEffect

    data class OnCharacterItemClicked(val characterId: Int) : EpisodeDetailSideEffect
}

data class EpisodeDetailState(
    val episodeError: Throwable? = null,
    val charactersError: Throwable? = null,
    val episode: Episode? = null,
    val characters: List<Character>? = null,
)