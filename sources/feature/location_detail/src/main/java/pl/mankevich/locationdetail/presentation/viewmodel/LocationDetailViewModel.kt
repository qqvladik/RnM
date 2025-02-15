package pl.mankevich.locationdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import pl.mankevich.coreui.mvi.SideEffects
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharactersByLocationIdUseCase
import pl.mankevich.domainapi.usecase.LoadLocationDetailUseCase
import pl.mankevich.locationdetail.getLocationId

class LocationDetailViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadLocationDetailUseCase: LoadLocationDetailUseCase,
    private val loadCharactersByLocationIdUseCase: LoadCharactersByLocationIdUseCase,
) : LocationDetailMviViewModel(
    initialStateWithEffects = LocationDetailStateWithEffects(
        state = LocationDetailState(),
        sideEffects = SideEffects<LocationDetailSideEffect>().add(
            LocationDetailSideEffect.OnLoadLocationRequested,
            LocationDetailSideEffect.OnLoadLocationsRequested
        )
    )
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<LocationDetailViewModel>

    private val locationId = savedStateHandle.getLocationId()

    override fun executeIntent(intent: LocationDetailIntent): Flow<Transform<LocationDetailStateWithEffects>> =
        when (intent) {
            is LocationDetailIntent.LoadLocation -> flowOf(
                LocationDetailTransforms.LoadLocation(locationId)
            )
                .flatMapMerge {
                    loadLocationDetailUseCase(locationId = locationId)
                        .map { LocationDetailTransforms.LoadLocationSuccess(it) }
                }

            is LocationDetailIntent.LoadCharacters -> flowOf(
                LocationDetailTransforms.LoadCharacters(locationId)
            ).flatMapMerge {
                loadCharactersByLocationIdUseCase(locationId = locationId)
                    .map { LocationDetailTransforms.LoadCharactersSuccess(it) }
            }

            is LocationDetailIntent.CharacterItemClick -> emptyFlow()
        }

    fun handleSideEffect(
        sideEffect: LocationDetailSideEffect,
    ) {
        when (sideEffect) {
            is LocationDetailSideEffect.OnCharacterItemClicked ->
                sendIntent(LocationDetailIntent.CharacterItemClick(sideEffect.characterId))

            is LocationDetailSideEffect.OnLoadLocationRequested ->
                sendIntent(LocationDetailIntent.LoadLocation)

            is LocationDetailSideEffect.OnLoadLocationsRequested ->
                sendIntent(LocationDetailIntent.LoadCharacters)
        }
    }
}
