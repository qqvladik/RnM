package pl.mankevich.characterdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import pl.mankevich.characterdetail.getCharacterId
import pl.mankevich.coreui.mvi.SideEffects
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase

class CharacterDetailViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadCharacterDetailUseCase: LoadCharacterDetailUseCase,
    private val loadEpisodesByCharacterIdUseCase: LoadEpisodesByCharacterIdUseCase,
) : CharacterDetailMviViewModel(
    initialStateWithEffects = CharacterDetailStateWithEffects(
        state = CharacterDetailState(),
        sideEffects = SideEffects<CharacterDetailSideEffect>().add(
            CharacterDetailSideEffect.OnLoadCharacterRequested,
            CharacterDetailSideEffect.OnLoadEpisodesRequested
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharacterDetailViewModel>

    val characterId = savedStateHandle.getCharacterId()

    override fun executeIntent(intent: CharacterDetailIntent): Flow<Transform<CharacterDetailStateWithEffects>> =
        when (intent) {
            is CharacterDetailIntent.LoadCharacter -> flowOf(
                CharacterDetailTransforms.LoadCharacter(characterId)
            ).flatMapMerge {
                try {
                    loadCharacterDetailUseCase(characterId = characterId)
                        .map { CharacterDetailTransforms.LoadCharacterSuccess(it) }
                } catch (e: Throwable) {
                    flowOf(CharacterDetailTransforms.LoadCharacterError(e))
                }
            }

//            {
//                val loadCharacterFlow = flowOf(
//                    CharacterDetailTransforms.LoadCharacter(characterId),
////                    CharacterDetailTransforms.LoadEpisodes(characterId)
//                )
//
//                val loadCharacterDetailFlow = try {
//                    loadCharacterDetailUseCase(characterId = characterId)
//                        .map { CharacterDetailTransforms.LoadCharacterSuccess(it) }
//                } catch (e: Throwable) {
//                    flowOf(CharacterDetailTransforms.LoadCharacterError(e))
//                }
//
//                flowOf(loadCharacterFlow, loadCharacterDetailFlow).flattenConcat()
//            }

            is CharacterDetailIntent.LoadEpisodes -> flowOf(
                CharacterDetailTransforms.LoadEpisodes(characterId)
            ).flatMapMerge {
                try {
                    loadEpisodesByCharacterIdUseCase(characterId = characterId)
                        .map { CharacterDetailTransforms.LoadEpisodesSuccess(it) }
                } catch (e: Throwable) {
                    flowOf(CharacterDetailTransforms.LoadEpisodesError(e))
                }
            }

            is CharacterDetailIntent.LocationItemClick -> flowOf(
                CharacterDetailTransforms.LocationItemClick(intent.locationId)
            )

            is CharacterDetailIntent.EpisodeItemClick -> flowOf(
                CharacterDetailTransforms.EpisodeItemClick(intent.episodeId)
            )

            CharacterDetailIntent.BackClick -> flowOf(
                CharacterDetailTransforms.BackClick
            )
        }

    fun handleSideEffect(
        sideEffect: CharacterDetailSideEffect,
//        navigateToCharactersList: (String, String, String, String) -> Unit, //TODO
        navigateToLocationDetail: (Int) -> Unit,
        navigateToEpisodeDetail: (Int) -> Unit,
        navigateBack: () -> Unit,
    ) {
        when (sideEffect) {
            is CharacterDetailSideEffect.OnLoadCharacterRequested ->
                sendIntent(CharacterDetailIntent.LoadCharacter)

            is CharacterDetailSideEffect.OnLoadEpisodesRequested ->
                sendIntent(CharacterDetailIntent.LoadEpisodes)

            is CharacterDetailSideEffect.OnLocationItemClicked ->
                navigateToLocationDetail(sideEffect.locationId)

            is CharacterDetailSideEffect.OnEpisodeItemClicked ->
                navigateToEpisodeDetail(sideEffect.episodeId)

            is CharacterDetailSideEffect.OnBackClicked -> navigateBack()
        }
    }
}
