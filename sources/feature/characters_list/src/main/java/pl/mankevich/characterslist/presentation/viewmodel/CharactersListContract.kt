package pl.mankevich.characterslist.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter

typealias CharactersListStateWithEffects = StateWithEffects<CharactersListState, CharactersListSideEffect>
typealias CharactersListMviViewModel = MviViewModel<CharactersListIntent, CharactersListStateWithEffects>

sealed interface CharactersListIntent {

    data class Init(val characterFilter: CharacterFilter) : CharactersListIntent, UniqueIntent

    data object Refresh : CharactersListIntent, UniqueIntent

    data class NameChanged(val name: String) : CharactersListIntent, UniqueIntent

    data class StatusChanged(val status: String) : CharactersListIntent, UniqueIntent

    data class SpeciesChanged(val species: String) : CharactersListIntent, UniqueIntent

    data class GenderChanged(val gender: String) : CharactersListIntent, UniqueIntent

    data class TypeChanged(val type: String) : CharactersListIntent, UniqueIntent

    data class CharacterItemClick(val characterId: Int) : CharactersListIntent

    data object BackClick : CharactersListIntent
}

sealed interface CharactersListSideEffect {

    data class NavigateToCharacterDetail(val characterId: Int) : CharactersListSideEffect

    data object NavigateBack : CharactersListSideEffect
}

@Immutable
data class CharactersListState(
    val characterFilter: CharacterFilter = CharacterFilter(),
    val isOnline: Boolean = false,
    // Flow is unstable, so it will always recompose https://issuetracker.google.com/issues/183495984
    // Currently there is no solution how to fit Paging in Unidirectional data flow (MVI)
    val characters: Flow<PagingData<Character>> = emptyFlow(),
    val statusLabelList: List<String> = listOf("Alive", "Dead", "Unknown"),
    val speciesLabelList: List<String> = listOf("Alien", "Human", "Humanoid", "Robot"),
    val genderLabelList: List<String> = listOf("Male", "Female", "Genderless", "Unknown"),
    val typeLabelList: List<String> = listOf("Parasite")
)