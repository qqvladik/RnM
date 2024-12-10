package pl.mankevich.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import javax.inject.Inject

class LoadCharactersListUseCaseImpl
@Inject constructor(
    private val characterRepository: CharacterRepository
) : LoadCharactersListUseCase {

    override suspend operator fun invoke(characterFilter: CharacterFilter): Flow<PagingData<Character>> {
        return characterRepository.getCharactersPageFlow(characterFilter)
    }
}