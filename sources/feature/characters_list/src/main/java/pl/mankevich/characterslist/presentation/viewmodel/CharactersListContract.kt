package pl.mankevich.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.mankevich.core.mvi.StateWithEffects
import pl.mankevich.model.Character
import pl.mankevich.model.Filter

typealias CharactersListStateWithEffects = StateWithEffects<CharactersListState, CharactersListSideEffect>

sealed class CharactersListAction {

    data class LoadCharacters(
        val instantRefresh: Boolean,
        val filter: Filter
    ) : CharactersListAction()//external call(f.e. usecase call)

    data class LoadCharactersSuccess(val characters: Flow<PagingData<Character>>) :
        CharactersListAction()

    data class CharacterItemClick(val characterId: Int) : CharactersListAction()
}

sealed interface CharactersListSideEffect {

    data class OnCharacterItemClick(val characterId: Int) : CharactersListSideEffect
}

data class CharactersListState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val filter: Filter = Filter(),
    val characters: Flow<PagingData<Character>> = emptyFlow(),
)