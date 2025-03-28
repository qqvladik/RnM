package pl.mankevich.characterslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.characterslist.getCharacterFilter
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.model.CharacterFilter

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class CharactersListViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadCharactersListUseCase: LoadCharactersListUseCase,
) : CharactersListMviViewModel(
    initialStateWithEffects = CharactersListStateWithEffects(
        state = CharactersListState()
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharactersListViewModel>

    private val currentState: CharactersListState
        get() = stateWithEffects.value.state

    override fun initialize() {
        sendIntent(CharactersListIntent.Init(savedStateHandle.getCharacterFilter()))
    }

    override fun executeIntent(intent: CharactersListIntent): Flow<Transform<CharactersListStateWithEffects>> =
        when (intent) {
            is CharactersListIntent.Init ->
                flow {
                    emit(CharactersListTransforms.Init(intent.characterFilter))
                    emitAll(loadCharacters(intent.characterFilter, instantRefresh = true))
                }

            CharactersListIntent.Refresh -> loadCharacters(
                currentState.characterFilter,
                instantRefresh = true
            )

            is CharactersListIntent.NameChanged -> flow {
                emit(CharactersListTransforms.ChangeName(name = intent.name))
                val newFilter = currentState.characterFilter.copy(name = intent.name)
                emitAll(loadCharacters(newFilter))
            }

            is CharactersListIntent.StatusChanged -> flow {
                emit(CharactersListTransforms.ChangeStatus(status = intent.status))
                val newFilter = currentState.characterFilter.copy(status = intent.status)
                emitAll(loadCharacters(newFilter))
            }

            is CharactersListIntent.SpeciesChanged -> flow {
                emit(CharactersListTransforms.ChangeSpecies(species = intent.species))
                val newFilter = currentState.characterFilter.copy(species = intent.species)
                emitAll(loadCharacters(newFilter))
            }

            is CharactersListIntent.GenderChanged -> flow {
                emit(CharactersListTransforms.ChangeGender(gender = intent.gender))
                val newFilter = currentState.characterFilter.copy(gender = intent.gender)
                emitAll(loadCharacters(newFilter))
            }

            is CharactersListIntent.TypeChanged -> flow {
                emit(CharactersListTransforms.ChangeType(type = intent.type))
                val newFilter = currentState.characterFilter.copy(type = intent.type)
                emitAll(loadCharacters(newFilter))
            }

            is CharactersListIntent.CharacterItemClick -> flowOf(
                CharactersListTransforms.CharacterItemClick(intent.characterId)
            )

            CharactersListIntent.BackClick -> flowOf(CharactersListTransforms.BackClick)
        }

    private fun loadCharacters(
        characterFilter: CharacterFilter,
        instantRefresh: Boolean = false
    ): Flow<Transform<CharactersListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
        emit(
            CharactersListTransforms.LoadCharactersListSuccess(
                loadCharactersListUseCase(characterFilter).cachedIn(viewModelScope)
            )
        )
    }
}
