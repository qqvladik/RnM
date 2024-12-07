package pl.mankevich.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.core.mvi.Transform
import pl.mankevich.model.Character

typealias CharactersListTransform = Transform<CharactersListStateWithEffects>

object CharactersListTransforms {

    data class ChangeFilters(
        val name: String? = null,
        val status: String? = null,
        val species: String? = null,
        val type: String? = null,
        val gender: String? = null
    ) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            val currentFilter = current.state.filter
            val newFilter = currentFilter.copy(
                name = name?: currentFilter.name,
                status = status?: currentFilter.status,
                species = species?: currentFilter.species,
                type = type?: currentFilter.type,
                gender = gender?: currentFilter.gender
            )
            return current.copy(
                state = current.state.copy(
                    isLoading = true,
                    filter = newFilter
                ),
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.OnLoadCharactersRequested(newFilter)
                )
            )
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
