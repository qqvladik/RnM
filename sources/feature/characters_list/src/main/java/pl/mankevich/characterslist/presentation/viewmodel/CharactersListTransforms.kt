package pl.mankevich.characterslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.ui.filter.addLabelIfUnique
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter

typealias CharactersListTransform = Transform<CharactersListStateWithEffects>

object CharactersListTransforms {

    data class Init(
        val filter: CharacterFilter,
    ) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    characterFilter = filter,
                    statusLabelList = current.state.statusLabelList.addLabelIfUnique(filter.status),
                    speciesLabelList = current.state.speciesLabelList.addLabelIfUnique(filter.species),
                    genderLabelList = current.state.genderLabelList.addLabelIfUnique(filter.gender),
                    typeLabelList = current.state.typeLabelList.addLabelIfUnique(filter.type),
                ),
            )
        }
    }

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
                )
            )
        }
    }

    data class CharacterItemClick(val characterId: Int) : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.NavigateToCharacterDetail(characterId)
                )
            )
        }
    }

    data object BackClick : CharactersListTransform {

        override fun reduce(current: CharactersListStateWithEffects): CharactersListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    CharactersListSideEffect.NavigateBack
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
