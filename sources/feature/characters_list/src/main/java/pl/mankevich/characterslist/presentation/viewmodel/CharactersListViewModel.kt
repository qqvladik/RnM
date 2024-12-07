package pl.mankevich.characterslist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.core.mvi.SideEffects
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
        state = CharactersListState(),
        sideEffects = SideEffects<CharactersListSideEffect>().add(
            CharactersListSideEffect.OnRefreshRequested(
                Filter()
            )
        )
    )
) {

    override fun executeIntent(intent: CharactersListIntent): Flow<Transform<CharactersListStateWithEffects>> =
        when (intent) {
            is CharactersListIntent.LoadCharacters -> loadCharacters(
                instantRefresh = false,
                intent.filter
            )

            is CharactersListIntent.Refresh -> loadCharacters(instantRefresh = true, intent.filter)

            is CharactersListIntent.NameFilterChanged -> flowOf(
                CharactersListTransforms.ChangeFilters(name = intent.name)
            )

            is CharactersListIntent.StatusFilterChanged -> flowOf(
                CharactersListTransforms.ChangeFilters(status = intent.status)
            )

            is CharactersListIntent.SpeciesFilterChanged -> flowOf(
                CharactersListTransforms.ChangeFilters(species = intent.species)
            )

            is CharactersListIntent.GenderFilterChanged -> flowOf(
                CharactersListTransforms.ChangeFilters(gender = intent.gender)
            )

            is CharactersListIntent.TypeFilterChanged -> flowOf(
                CharactersListTransforms.ChangeFilters(type = intent.type)
            )

            is CharactersListIntent.CharacterItemClick -> flowOf(
                CharactersListTransforms.CharacterItemClick(intent.characterId)
            )
        }

    private fun loadCharacters(
        instantRefresh: Boolean,
        filter: Filter
    ): Flow<Transform<CharactersListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
        emit(
            CharactersListTransforms.LoadCharactersListSuccess(
                loadCharactersListUseCase(filter).cachedIn(viewModelScope)
            )
        )
    }

    fun handleSideEffect(
        sideEffect: CharactersListSideEffect,
        onCharacterItemClick: (Int) -> Unit
    ) {
        when (sideEffect) {
            is CharactersListSideEffect.OnCharacterItemClicked ->
                onCharacterItemClick(sideEffect.characterId)

            is CharactersListSideEffect.OnLoadCharactersRequested ->
                sendIntent(CharactersListIntent.LoadCharacters(sideEffect.filter))

            is CharactersListSideEffect.OnRefreshRequested ->
                sendIntent(CharactersListIntent.Refresh(sideEffect.filter))
        }
    }
}