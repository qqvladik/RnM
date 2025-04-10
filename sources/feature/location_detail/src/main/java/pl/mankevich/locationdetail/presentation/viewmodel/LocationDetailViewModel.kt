package pl.mankevich.locationdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
    )
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<LocationDetailViewModel>

    val locationId = savedStateHandle.getLocationId()

    override fun initialize() {
        sendIntent(LocationDetailIntent.LoadLocationDetail)
    }

    override fun executeIntent(intent: LocationDetailIntent): Flow<Transform<LocationDetailStateWithEffects>> =
        when (intent) {
            LocationDetailIntent.LoadLocationDetail ->
                loadLocation().onEach { characterDetailTransform ->
                    if (
                        characterDetailTransform is LocationDetailTransforms.LoadLocationSuccess
                        && characterDetailTransform.isOnline != null
                    ) {
                        sendIntent(LocationDetailIntent.LoadCharacters)
                    }
                }

            LocationDetailIntent.LoadCharacters -> loadCharacters()

            is LocationDetailIntent.DimensionFilterClick -> flowOf(
                LocationDetailTransforms.DimensionFilterClick(intent.dimension)
            )

            is LocationDetailIntent.TypeFilterClick -> flowOf(
                LocationDetailTransforms.TypeFilterClick(intent.type)
            )

            is LocationDetailIntent.CharacterItemClick -> flowOf(
                LocationDetailTransforms.CharacterItemClick(intent.characterId)
            )

            LocationDetailIntent.BackClick -> flowOf(
                LocationDetailTransforms.BackClick
            )
        }

    private fun loadLocation(): Flow<Transform<LocationDetailStateWithEffects>> =
        flow {
            emit(LocationDetailTransforms.LoadLocation(locationId))

            // Workaround to give some time for indicator from pullToRefresh to consume all states
            // during recompositions. Because otherwise pullToRefresh indicator doesn't hide when
            // isLoading state updates very quickly (e.g. in offline mode)
            delay(20)

            val result = loadLocationDetailUseCase(locationId = locationId)
                .map {
                    LocationDetailTransforms.LoadLocationSuccess(
                        isOnline = it.isOnline,
                        location = it.location
                    )
                }
                .catch {
                    emit(LocationDetailTransforms.LoadLocationError(it))
                }
            emitAll(result)
        }

    private fun loadCharacters(): Flow<Transform<LocationDetailStateWithEffects>> =
        flow {
            emit(LocationDetailTransforms.LoadCharacters(locationId))
            val result = loadCharactersByLocationIdUseCase(locationId = locationId)
                .map { LocationDetailTransforms.LoadCharactersSuccess(it) }
                .catch {
                    emit(LocationDetailTransforms.LoadCharactersError(it))
                }
            emitAll(result)
        }
}
