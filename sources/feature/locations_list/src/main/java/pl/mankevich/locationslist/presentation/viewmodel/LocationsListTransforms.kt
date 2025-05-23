package pl.mankevich.locationslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.ui.filter.addLabelIfUnique
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

typealias LocationsListTransform = Transform<LocationsListStateWithEffects>

object LocationsListTransforms {

    data class Init(
        val filter: LocationFilter
    ) : LocationsListTransform {

        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    locationFilter = filter,
                    typeLabelList = current.state.typeLabelList.addLabelIfUnique(filter.type),
                    dimensionLabelList = current.state.dimensionLabelList.addLabelIfUnique(filter.dimension),
                ),
            )
        }
    }

    data class ChangeName(
        val name: String
    ) : LocationsListTransform {

        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            val currentFilter = current.state.locationFilter
            val newFilter = currentFilter.copy(name = name)
            return current.copy(
                state = current.state.copy(
                    locationFilter = newFilter,
                ),
            )
        }
    }

    data class ChangeType(
        val type: String
    ) : LocationsListTransform {
        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            val currentFilter = current.state.locationFilter
            val newFilter = currentFilter.copy(type = type)
            return current.copy(
                state = current.state.copy(
                    locationFilter = newFilter,
                    typeLabelList = current.state.typeLabelList.addLabelIfUnique(type),
                ),
            )
        }
    }

    data class ChangeDimension(
        val dimension: String
    ) : LocationsListTransform {
        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            val currentFilter = current.state.locationFilter
            val newFilter = currentFilter.copy(dimension = dimension)
            return current.copy(
                state = current.state.copy(
                    locationFilter = newFilter,
                    dimensionLabelList = current.state.dimensionLabelList.addLabelIfUnique(dimension),
                ),
            )
        }
    }

    data class LocationItemClick(val characterId: Int) : LocationsListTransform {

        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    LocationsListSideEffect.NavigateToLocationDetail(characterId)
                )
            )
        }
    }

    data object BackClick : LocationsListTransform {

        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    LocationsListSideEffect.NavigateBack
                )
            )
        }
    }

    data class LoadLocationsListSuccess(
        val isOnline: Boolean,
        val locations: Flow<PagingData<Location>>
    ) : LocationsListTransform {

        override fun reduce(current: LocationsListStateWithEffects): LocationsListStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    isOnline = isOnline,
                    locations = locations
                )
            )
        }
    }
}

