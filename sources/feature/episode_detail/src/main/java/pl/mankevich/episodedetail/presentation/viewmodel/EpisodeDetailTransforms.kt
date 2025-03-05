package pl.mankevich.episodedetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias EpisodeDetailTransform = Transform<EpisodeDetailStateWithEffects>

object EpisodeDetailTransforms {

    data class LoadEpisode(val episodeId: Int) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(),
                sideEffects = current.sideEffects.add(
                    EpisodeDetailSideEffect.OnLoadEpisodeRequested
                )
            )
        }
    }

    data class LoadEpisodeSuccess(val episode: Episode) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    episode = episode,
                    episodeError = null
                )
            )
        }
    }

    data class LoadEpisodeError(val error: Throwable) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    episode = null,
                    episodeError = error
                )
            )
        }
    }

    data class LoadCharacters(val episodeId: Int) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(),
                sideEffects = current.sideEffects.add(
                    EpisodeDetailSideEffect.OnLoadEpisodesRequested
                )
            )
        }
    }

    data class LoadCharactersSuccess(val characters: List<Character>) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characters = characters
                )
            )
        }
    }

    data class LoadCharacterError(val error: Throwable) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characters = null
                )
            )
        }
    }


    data class CharacterItemClick(val characterId: Int) : EpisodeDetailTransform {

        override fun reduce(current: EpisodeDetailStateWithEffects): EpisodeDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    EpisodeDetailSideEffect.OnCharacterItemClicked(characterId)
                )
            )
        }
    }
}