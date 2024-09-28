package pl.mankevich.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.mankevich.core.mvi.MviViewModel
import pl.mankevich.core.mvi.StateWithEffects
import pl.mankevich.core.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.Filter

typealias CharactersListStateWithEffects = StateWithEffects<CharactersListState, CharactersListSideEffect>
typealias CharactersListMviViewModel = MviViewModel<CharactersListIntent, CharactersListStateWithEffects>

sealed class CharactersListIntent {

    data class LoadCharacters(val filter: Filter) : CharactersListIntent(), UniqueIntent

    data class Refresh(val filter: Filter) : CharactersListIntent()

    data class CharacterItemClick(val characterId: Int) : CharactersListIntent()
}

sealed interface CharactersListSideEffect {

    data class OnCharacterItemClicked(val characterId: Int) : CharactersListSideEffect
}

data class CharactersListState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val filter: Filter = Filter(),
    val characters: Flow<PagingData<Character>> = emptyFlow(),
)