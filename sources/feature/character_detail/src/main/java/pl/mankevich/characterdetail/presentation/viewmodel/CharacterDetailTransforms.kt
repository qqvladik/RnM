package pl.mankevich.characterdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailTransform = Transform<CharacterDetailStateWithEffects>

object CharacterDetailTransforms {

    data class LoadCharacter(val characterId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(character = null),
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.OnLoadCharacterRequested
                )
            )
        }
    }

    data class LoadCharacterSuccess(val character: Character) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    character = character,
                    characterError = null
                )
            )
        }
    }

    data class LoadCharacterError(val error: Throwable) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    character = null,
                    characterError = error
                )
            )
        }
    }

    data class LoadEpisodes(val characterId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(episodes = null),
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.OnLoadEpisodesRequested
                )
            )
        }
    }

    data class LoadEpisodesSuccess(val episodes: List<Episode>) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    episodes = episodes,
                    episodesError = null
                )
            )
        }
    }

    data class LoadEpisodesError(val error: Throwable) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    episodes = null,
                    episodesError = error
                )
            )
        }
    }
}