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

    data class Refresh(val filter: Filter) : CharactersListIntent(), UniqueIntent

    data class CharacterItemClick(val characterId: Int) : CharactersListIntent()

    data class NameFilterChanged(val name: String) : CharactersListIntent()

    data class StatusFilterChanged(val status: String) : CharactersListIntent()

    data class SpeciesFilterChanged(val species: String) : CharactersListIntent()

    data class GenderFilterChanged(val gender: String) : CharactersListIntent()

    data class TypeFilterChanged(val type: String) : CharactersListIntent()
}

sealed interface CharactersListSideEffect {

    data class OnCharacterItemClicked(val characterId: Int) : CharactersListSideEffect

    data class OnLoadCharactersRequested(val filter: Filter) : CharactersListSideEffect

    data class OnRefreshRequested(val filter: Filter) : CharactersListSideEffect
}

data class CharactersListState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val filter: Filter = Filter(),
    val characters: Flow<PagingData<Character>> = emptyFlow(),
)