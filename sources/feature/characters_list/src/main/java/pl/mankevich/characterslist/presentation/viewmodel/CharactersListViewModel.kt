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
import pl.mankevich.characterslist.getCharacterFilterTypesafe
import pl.mankevich.coreui.mvi.SideEffects
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class CharactersListViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadCharactersListUseCase: LoadCharactersListUseCase,
) : CharactersListMviViewModel(
    initialStateWithEffects = CharactersListStateWithEffects(
        state = CharactersListState(),
        sideEffects = SideEffects<CharactersListSideEffect>().add(
            CharactersListSideEffect.OnInitRequested(
                savedStateHandle.getCharacterFilterTypesafe()
            )
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharactersListViewModel>

    override fun executeIntent(intent: CharactersListIntent): Flow<Transform<CharactersListStateWithEffects>> =
        when (intent) {
            is CharactersListIntent.Init -> flowOf(
                CharactersListTransforms.Init(intent.characterFilter)
            )

            is CharactersListIntent.LoadCharacters -> flow {
                delay(QUERY_INPUT_DELAY_MILLIS)
                emit(
                    CharactersListTransforms.LoadCharactersListSuccess(
                        loadCharactersListUseCase(intent.characterFilter).cachedIn(viewModelScope)
                    )
                )
            }

            is CharactersListIntent.Refresh -> flow {
                emit(
                    CharactersListTransforms.LoadCharactersListSuccess(
                        loadCharactersListUseCase(intent.characterFilter).cachedIn(viewModelScope)
                    )
                )
            }

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

    fun handleSideEffect(
        sideEffect: CharactersListSideEffect,
        onCharacterItemClick: (Int) -> Unit
    ) {
        when (sideEffect) {
            is CharactersListSideEffect.OnInitRequested ->
                sendIntent(CharactersListIntent.Init(sideEffect.characterFilter))

            is CharactersListSideEffect.OnCharacterItemClicked ->
                onCharacterItemClick(sideEffect.characterId)

            is CharactersListSideEffect.OnLoadCharactersRequested ->
                sendIntent(CharactersListIntent.LoadCharacters(sideEffect.characterFilter))

            is CharactersListSideEffect.OnRefreshRequested ->
                sendIntent(CharactersListIntent.Refresh(sideEffect.characterFilter))
        }
    }
}
