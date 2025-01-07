package pl.mankevich.locationdetail.presentation.viewmodel

import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Location

typealias LocationDetailTransform = Transform<LocationDetailStateWithEffects>

object LocationDetailTransforms {

    data class LoadLocation(val locationId: Int) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(),
                sideEffects = current.sideEffects.add(
                    LocationDetailSideEffect.OnLoadLocationRequested
                )
            )
        }
    }

    data class LoadLocationSuccess(val location: Location) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    location = location
                )
            )
        }
    }

    data class LoadCharacters(val locationId: Int) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(),
                sideEffects = current.sideEffects.add(
                    LocationDetailSideEffect.OnLoadLocationsRequested
                )
            )
        }
    }

    data class LoadCharactersSuccess(val characters: List<Character>) : LocationDetailTransform {

        override fun reduce(current: LocationDetailStateWithEffects): LocationDetailStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characters = characters
                )
            )
        }
    }
}