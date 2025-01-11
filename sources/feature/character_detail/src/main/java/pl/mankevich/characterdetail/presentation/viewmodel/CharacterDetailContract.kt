package pl.mankevich.characterdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailStateWithEffects = StateWithEffects<CharacterDetailState, CharacterDetailSideEffect>
typealias CharacterDetailMviViewModel = MviViewModel<CharacterDetailIntent, CharacterDetailStateWithEffects>

sealed class CharacterDetailIntent {

    data object LoadCharacter : CharacterDetailIntent(), UniqueIntent

    data object LoadEpisodes : CharacterDetailIntent(), UniqueIntent

    data class EpisodeItemClick(val episodeId: Int) : CharacterDetailIntent()
}

sealed interface CharacterDetailSideEffect {

    data object OnLoadCharacterRequested : CharacterDetailSideEffect

    data object OnLoadEpisodesRequested : CharacterDetailSideEffect

    data class OnEpisodeItemClicked(val episodeId: Int) : CharacterDetailSideEffect
}

data class CharacterDetailState(
    val characterError: Throwable? = null,
    val episodesError: Throwable? = null,
    val character: Character? = null,
    val episodes: List<Episode>? = null,
)