package pl.mankevich.characterdetail.presentation.viewmodel

import pl.mankevich.core.mvi.MviViewModel
import pl.mankevich.core.mvi.StateWithEffects
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailStateWithEffects = StateWithEffects<CharacterDetailState, CharacterDetailSideEffect>
typealias CharacterDetailMviViewModel = MviViewModel<CharacterDetailIntent, CharacterDetailStateWithEffects>

sealed class CharacterDetailIntent {

    data object LoadCharacter : CharacterDetailIntent()

    data object LoadEpisodes : CharacterDetailIntent()

    data class EpisodeItemClick(val episodeId: Int) : CharacterDetailIntent()
}

sealed interface CharacterDetailSideEffect {

    data class OnEpisodeItemClicked(val episodeId: Int) : CharacterDetailSideEffect
}

data class CharacterDetailState(
    val isCharacterLoading: Boolean = true,
    val isEpisodesLoading: Boolean = true,
    val error: Throwable? = null,
    val character: Character? = null,
    val episodes: List<Episode>? = null,
)