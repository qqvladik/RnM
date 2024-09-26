package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.model.Character
import javax.inject.Inject

class LoadCharacterDetailUseCaseImpl
@Inject constructor(
    private val characterRepository: CharacterRepository
) : LoadCharacterDetailUseCase {

    override operator fun invoke(characterId: Int): Flow<Character> =
        characterRepository.getCharacterDetail(characterId)
}