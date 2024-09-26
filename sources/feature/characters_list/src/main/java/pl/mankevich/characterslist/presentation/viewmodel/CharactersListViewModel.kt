package pl.mankevich.characterslist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.mankevich.core.mvi.MviViewModel
import pl.mankevich.core.mvi.StateWithEffects
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import javax.inject.Inject

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class CharactersListViewModel
@Inject constructor(
    private val loadCharactersListUseCase: LoadCharactersListUseCase,
) : MviViewModel<CharactersListAction, CharactersListState, CharactersListSideEffect>(
    initialStateWithEffects = StateWithEffects(
        state = CharactersListState()
    )
) {
    private var loadJob: Job? = null //TODO move this logic to MviViewModel using mvi Reducer by adidas as example

    override fun executeAction(action: CharactersListAction) { //execute external actions(f.e. usecase call)
        when (action) {
            is CharactersListAction.LoadCharacters -> {
                loadJob?.cancel()
                loadJob = viewModelScope.launch { //TODO move this logic to MviViewModel using mvi Reducer by adidas as example
                    if (!action.instantRefresh) {
                        delay(QUERY_INPUT_DELAY_MILLIS)
                    }
                    sendAction(
                        CharactersListAction.LoadCharactersSuccess(//TODO add handling error results from domain layer
                            loadCharactersListUseCase(action.filter)
                                .cachedIn(viewModelScope)
                        )
                    )
                }
            }

            else -> {}
        }
    }

    override fun CharactersListStateWithEffects.reduce(action: CharactersListAction): CharactersListStateWithEffects {
        return when (action) {
            is CharactersListAction.LoadCharacters -> {
                copy(state = state.copy(isLoading = true, filter = action.filter))
            }

            is CharactersListAction.CharacterItemClick -> {
                copy(
                    sideEffects = sideEffects.add(
                        CharactersListSideEffect.OnCharacterItemClick(
                            action.characterId
                        )
                    )
                )
            }

            is CharactersListAction.LoadCharactersSuccess -> {
                copy(state = state.copy(isLoading = false, characters = action.characters))
            }
        }
    }
}