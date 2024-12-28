package pl.mankevich.characterdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailTransform = Transform<CharacterDetailStateWithEffects>

object CharacterDetailTransforms {

    data class LoadCharacter(val characterId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(isCharacterLoading = true),
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
                    isCharacterLoading = false,
                    character = character
                )
            )
        }
    }

    data class LoadEpisodes(val characterId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(isEpisodesLoading = true),
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
                    isEpisodesLoading = false,
                    episodes = episodes
                )
            )
        }
    }
}