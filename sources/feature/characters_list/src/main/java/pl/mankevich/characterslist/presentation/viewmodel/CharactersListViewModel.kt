package pl.mankevich.characterslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.characterslist.getCharacterFilter
import pl.mankevich.core.mvi.SideEffects
import pl.mankevich.core.mvi.Transform
import pl.mankevich.core.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.model.CharacterFilter

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class CharactersListViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadCharactersListUseCase: LoadCharactersListUseCase,
) : CharactersListMviViewModel(
    initialStateWithEffects = CharactersListStateWithEffects(
        state = CharactersListState(
            characterFilter = savedStateHandle.getCharacterFilter()
        ),
        sideEffects = SideEffects<CharactersListSideEffect>().add(
            CharactersListSideEffect.OnRefreshRequested(
                savedStateHandle.getCharacterFilter()
            )
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharactersListViewModel>

    override fun executeIntent(intent: CharactersListIntent): Flow<Transform<CharactersListStateWithEffects>> =
        when (intent) {
            is CharactersListIntent.LoadCharacters -> loadCharacters(
                instantRefresh = false,
                intent.characterFilter
            )

            is CharactersListIntent.Refresh -> loadCharacters(
                instantRefresh = true,
                intent.characterFilter
            )

            is CharactersListIntent.NameChanged -> flowOf(
                CharactersListTransforms.ChangeName(name = intent.name)
            )

            is CharactersListIntent.StatusChanged -> flowOf(
                CharactersListTransforms.ChangeStatus(status = intent.status)
            )

            is CharactersListIntent.SpeciesChanged -> flowOf(
                CharactersListTransforms.ChangeSpecies(species = intent.species)
            )

            is CharactersListIntent.GenderChanged -> flowOf(
                CharactersListTransforms.ChangeGender(gender = intent.gender)
            )

            is CharactersListIntent.TypeChanged -> flowOf(
                CharactersListTransforms.ChangeType(type = intent.type)
            )

            is CharactersListIntent.CharacterItemClick -> flowOf(
                CharactersListTransforms.CharacterItemClick(intent.characterId)
            )
        }

    private fun loadCharacters(
        instantRefresh: Boolean,
        characterFilter: CharacterFilter
    ): Flow<Transform<CharactersListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
        emit(
            CharactersListTransforms.LoadCharactersListSuccess(
                loadCharactersListUseCase(characterFilter).cachedIn(viewModelScope)
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
                sendIntent(CharactersListIntent.LoadCharacters(sideEffect.characterFilter))

            is CharactersListSideEffect.OnRefreshRequested ->
                sendIntent(CharactersListIntent.Refresh(sideEffect.characterFilter))
        }
    }
}