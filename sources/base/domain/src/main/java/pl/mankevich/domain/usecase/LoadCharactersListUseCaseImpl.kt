package pl.mankevich.domain.usecase

import pl.mankevich.dataapi.dto.CharactersResultDto
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.domainapi.result.CharactersResult
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.model.CharacterFilter
import javax.inject.Inject

class LoadCharactersListUseCaseImpl
@Inject constructor(
    private val characterRepository: CharacterRepository
) : LoadCharactersListUseCase {

    override suspend operator fun invoke(characterFilter: CharacterFilter): CharactersResult {
        return characterRepository.getCharactersPageFlow(characterFilter).mapToResult()
    }
}

fun CharactersResultDto.mapToResult() = CharactersResult(
    isOnline = isOnline,
    characters = characters
)