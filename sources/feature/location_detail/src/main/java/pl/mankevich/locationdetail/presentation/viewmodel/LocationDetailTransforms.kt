package pl.mankevich.locationdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Location

typealias LocationDetailTransform = Transform<LocationDetailStateWithEffects>

object LocationDetailTransforms {

    data class LoadLocation(val locationId: Int) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    location = null,
                    locationError = null
                ),
            )
        }
    }

    data class LoadLocationSuccess(val location: Location) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    location = location,
                    locationError = null
                )
            )
        }
    }

    data class LoadLocationError(val error: Throwable) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    location = null,
                    locationError = error
                )
            )
        }
    }

    data class LoadCharacters(val locationId: Int) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characters = null,
                    charactersError = null
                ),
            )
        }
    }

    data class LoadCharactersSuccess(val characters: List<Character>) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characters = characters,
                    charactersError = null
                )
            )
        }
    }

    data class LoadCharactersError(val error: Throwable) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characters = null,
                    charactersError = error
                )
            )
        }
    }

    data class DimensionFilterClick(val dimension: String) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    LocationDetailSideEffect.NavigateToLocationsListByDimension(dimension)
                )
            )
        }
    }

    data class TypeFilterClick(val type: String) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    LocationDetailSideEffect.NavigateToLocationsListByType(type)
                )
            )
        }
    }

    data class CharacterItemClick(val characterId: Int) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    LocationDetailSideEffect.NavigateToCharacterDetail(characterId)
                )
            )
        }
    }

    data object BackClick : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    LocationDetailSideEffect.NavigateBack
                )
            )
        }
    }
}