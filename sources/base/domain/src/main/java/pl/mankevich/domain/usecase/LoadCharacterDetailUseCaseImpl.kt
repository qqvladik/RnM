package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.dataapi.dto.CharacterDetailResultDto
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.domainapi.result.CharacterDetailResult
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import javax.inject.Inject

class LoadCharacterDetailUseCaseImpl
@Inject constructor(
    private val characterRepository: CharacterRepository
) : LoadCharacterDetailUseCase {

    override operator fun invoke(characterId: Int): Flow<CharacterDetailResult> =
        characterRepository.getCharacterDetail(characterId).map { it.mapToResult() }
}

fun CharacterDetailResultDto.mapToResult() = CharacterDetailResult(
    isOnline = isOnline,
    character = character
)