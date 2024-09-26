package pl.mankevich.characterdetail.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.mankevich.core.mvi.StateWithEffects
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailStateWithEffects = StateWithEffects<CharacterDetailState, CharacterDetailSideEffect>

sealed class CharacterDetailAction {

    data object LoadCharacter : CharacterDetailAction()//external call(f.e. usecase call)

    data object LoadEpisodes : CharacterDetailAction()

    data class LoadCharacterSuccess(val character: Flow<Character>) : CharacterDetailAction()

    data class LoadEpisodesSuccess(val episodes: Flow<List<Episode>>) : CharacterDetailAction()

    data class EpisodeItemClick(val episodeId: Int) : CharacterDetailAction()
}

sealed interface CharacterDetailSideEffect {

    data class OnEpisodeItemClick(val episodeId: Int) : CharacterDetailSideEffect
}

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val isEpisodesLoading: Boolean = true,
    val error: Throwable? = null,
    val character: Flow<Character> = emptyFlow(),
    val episodes: Flow<List<Episode>> = emptyFlow(),
)