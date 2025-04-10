package pl.mankevich.characterdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

typealias CharacterDetailTransform = Transform<CharacterDetailStateWithEffects>

object CharacterDetailTransforms {

    data class LoadCharacter(val characterId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    isOnline = null,
                    characterError = null
                ),
            )
        }
    }

    data class LoadCharacterSuccess(
        val isOnline: Boolean?,
        val character: Character?
    ) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    isOnline = isOnline,
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
                state = current.state.copy(
                    episodes = null,
                    episodesError = null
                ),
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

    data class StatusFilterClick(val status: String) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateToCharactersListByStatus(status)
                )
            )
        }
    }

    data class SpeciesFilterClick(val species: String) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateToCharactersListBySpecies(species)
                )
            )
        }
    }

    data class GenderFilterClick(val gender: String) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateToCharactersListByGender(gender)
                )
            )
        }
    }

    data class TypeFilterClick(val type: String) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateToCharactersListByType(type)
                )
            )
        }
    }

    data class LocationItemClick(val locationId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateToLocationDetail(locationId)
                )
            )
        }
    }

    data class EpisodeItemClick(val episodeId: Int) : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateToEpisodeDetail(episodeId)
                )
            )
        }
    }

    data object BackClick : CharacterDetailTransform {

        override fun reduce(current: CharacterDetailStateWithEffects): CharacterDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharacterDetailSideEffect.NavigateBack
                )
            )
        }
    }
}