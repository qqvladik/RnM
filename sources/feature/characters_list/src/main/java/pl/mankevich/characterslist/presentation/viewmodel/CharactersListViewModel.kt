package pl.mankevich.characterslist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.core.mvi.Transform
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.model.Filter
import javax.inject.Inject

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class CharactersListViewModel
@Inject constructor(
    private val loadCharactersListUseCase: LoadCharactersListUseCase,
) : CharactersListMviViewModel(
    initialStateWithEffects = CharactersListStateWithEffects(
        state = CharactersListState()
    )
) {

    override fun executeIntent(intent: CharactersListIntent): Flow<Transform<CharactersListStateWithEffects>> =
        when (intent) {
            is CharactersListIntent.LoadCharacters -> loadCharacters(
                instantRefresh = false,
                intent.filter
            )

            is CharactersListIntent.Refresh -> loadCharacters(instantRefresh = true, intent.filter)

            is CharactersListIntent.CharacterItemClick -> flowOf(
                CharactersListTransforms.CharacterItemClick(intent.characterId)
            )
        }

    private fun loadCharacters(
        instantRefresh: Boolean,
        filter: Filter
    ): Flow<Transform<CharactersListStateWithEffects>> = flow {
        emit(CharactersListTransforms.LoadCharactersList(filter))

        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)

        emit(
            CharactersListTransforms.LoadCharactersListSuccess(
                loadCharactersListUseCase(filter).cachedIn(viewModelScope)
            )
        )
    }
}