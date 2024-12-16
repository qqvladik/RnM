package pl.mankevich.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.core.mvi.Transform
import pl.mankevich.model.Character
import kotlin.collections.plus

typealias CharactersListTransform = Transform<CharactersListStateWithEffects>

object CharactersListTransforms {

    data class ChangeName(
        val name: String
    ) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            val currentFilter = current.state.characterFilter
            val newFilter = currentFilter.copy(name = name)
            return current.copy(
                state = current.state.copy(
                    characterFilter = newFilter,
                ),
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.OnLoadCharactersRequested(newFilter)
                )
            )
        }
    }

    data class ChangeStatus(
        val status: String
    ) : CharactersListTransform {
        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            val currentFilter = current.state.characterFilter
            val newFilter = currentFilter.copy(status = status)
            return current.copy(
                state = current.state.copy(
                    characterFilter = newFilter,
                    statusLabelList = current.state.statusLabelList.addLabelIfUnique(status),
                ),
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.OnLoadCharactersRequested(newFilter)
                )
            )
        }
    }

    data class ChangeSpecies(
        val species: String
    ) : CharactersListTransform {
        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            val currentFilter = current.state.characterFilter
            val newFilter = currentFilter.copy(species = species)
            return current.copy(
                state = current.state.copy(
                    characterFilter = newFilter,
                    speciesLabelList = current.state.speciesLabelList.addLabelIfUnique(species),
                ),
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.OnLoadCharactersRequested(newFilter)
                )
            )
        }
    }

    data class ChangeGender(
        val gender: String
    ) : CharactersListTransform {
        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            val currentFilter = current.state.characterFilter
            val newFilter = currentFilter.copy(gender = gender)
            return current.copy(
                state = current.state.copy(
                    characterFilter = newFilter,
                    genderLabelList = current.state.genderLabelList.addLabelIfUnique(gender),
                ),
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.OnLoadCharactersRequested(newFilter)
                )
            )
        }
    }

    data class ChangeType(
        val type: String
    ) : CharactersListTransform {
        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            val currentFilter = current.state.characterFilter
            val newFilter = currentFilter.copy(type = type)
            return current.copy(
                state = current.state.copy(
                    characterFilter = newFilter,
                    typeLabelList = current.state.typeLabelList.addLabelIfUnique(type),
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
                    characters = characters
                )
            )
        }
    }
}

private fun List<String>.addLabelIfUnique(filter: String): List<String> {
    if (filter.isBlank()) return this
    if (this.any { it.equals(filter, ignoreCase = true) }) return this
    return this.plus(filter)
}
