package pl.mankevich.characterdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pl.mankevich.characterdetail.getCharacterId
import pl.mankevich.core.mvi.MviViewModel
import pl.mankevich.core.mvi.StateWithEffects
import pl.mankevich.core.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase

class CharacterDetailViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadCharacterDetailUseCase: LoadCharacterDetailUseCase,
    private val loadEpisodesByCharacterIdUseCase: LoadEpisodesByCharacterIdUseCase,
) : MviViewModel<CharacterDetailAction, CharacterDetailState, CharacterDetailSideEffect>(
    initialStateWithEffects = StateWithEffects(
        state = CharacterDetailState()
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharacterDetailViewModel>

    override fun executeAction(action: CharacterDetailAction) { //execute external actions(f.e. usecase call)
        when (action) {
            is CharacterDetailAction.LoadCharacter -> {
                sendAction(
                    CharacterDetailAction.LoadCharacterSuccess(
                        loadCharacterDetailUseCase(characterId = savedStateHandle.getCharacterId())//TODO .stateIn(viewModelScope)
                    )
                )
            }

            is CharacterDetailAction.LoadEpisodes -> {
                sendAction(
                    CharacterDetailAction.LoadEpisodesSuccess(
                        loadEpisodesByCharacterIdUseCase(characterId = savedStateHandle.getCharacterId())
                    )
                )
            }

            else -> {}
        }
    }

    override fun CharacterDetailStateWithEffects.reduce(action: CharacterDetailAction): CharacterDetailStateWithEffects {
        return when (action) {
            is CharacterDetailAction.EpisodeItemClick -> {
                copy(
                    sideEffects = sideEffects.add(
                        CharacterDetailSideEffect.OnEpisodeItemClick(
                            action.episodeId
                        )
                    )
                )
            }

            is CharacterDetailAction.LoadCharacter -> {
                copy(state = state.copy(isLoading = true))
            }

            is CharacterDetailAction.LoadCharacterSuccess -> {
                copy(state = state.copy(isLoading = false, character = action.character))
            }

            CharacterDetailAction.LoadEpisodes -> {
                copy(state = state.copy(isEpisodesLoading = true))
            }

            is CharacterDetailAction.LoadEpisodesSuccess -> {
                copy(state = state.copy(isEpisodesLoading = false, episodes = action.episodes))
            }
        }
    }
}
