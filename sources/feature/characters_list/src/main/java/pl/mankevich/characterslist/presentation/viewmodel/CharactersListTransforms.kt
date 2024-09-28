package pl.mankevich.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.core.mvi.Transform
import pl.mankevich.model.Character
import pl.mankevich.model.Filter

typealias CharactersListTransform = Transform<CharactersListStateWithEffects>

object CharactersListTransforms {

    data class LoadCharactersList(val filter: Filter) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            return current.copy(state = current.state.copy(isLoading = true, filter = filter))
        }
    }

    data class CharacterItemClick(val characterId: Int) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.OnCharacterItemClicked(characterId)
                )
            )
        }
    }

    data class LoadCharactersListSuccess(
        val characters: Flow<PagingData<Character>>
    ) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    isLoading = false,
                    characters = characters
                )
            )
        }
    }
}
