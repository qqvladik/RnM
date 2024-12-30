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

sealed class CharactersListIntent {

    data class LoadCharacters(val characterFilter: CharacterFilter) : CharactersListIntent(), UniqueIntent

    data class Refresh(val characterFilter: CharacterFilter) : CharactersListIntent(), UniqueIntent

    data class CharacterItemClick(val characterId: Int) : CharactersListIntent()

    data class NameChanged(val name: String) : CharactersListIntent()

    data class StatusChanged(val status: String) : CharactersListIntent()

    data class SpeciesChanged(val species: String) : CharactersListIntent()

    data class GenderChanged(val gender: String) : CharactersListIntent()

    data class TypeChanged(val type: String) : CharactersListIntent()
}

sealed interface CharactersListSideEffect {

    data class OnCharacterItemClicked(val characterId: Int) : CharactersListSideEffect

    data class OnLoadCharactersRequested(val characterFilter: CharacterFilter) :
        CharactersListSideEffect

    data class OnRefreshRequested(val characterFilter: CharacterFilter) : CharactersListSideEffect
}

@Immutable
data class CharactersListState(
    val characterFilter: CharacterFilter = CharacterFilter(),
    // Flow is unstable, so it will always recompose https://issuetracker.google.com/issues/183495984
    // Currently there is no solution how to fit Paging in Unidirectional data flow (MVI)
    val characters: Flow<PagingData<Character>> = emptyFlow(),
    val statusLabelList: List<String> = listOf("Alive", "Dead", "Unknown"),
    val speciesLabelList: List<String> = listOf("Alien", "Human", "Humanoid", "Robot"),
    val genderLabelList: List<String> = listOf("Male", "Female", "Genderless", "Unknown"),
    val typeLabelList: List<String> = listOf("Parasite")
)