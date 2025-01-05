package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.domainapi.usecase.LoadCharactersByLocationIdUseCase
import pl.mankevich.model.Character
import javax.inject.Inject

class LoadCharactersByLocationIdUseCaseImpl
@Inject constructor(
    private val characterRepository: CharacterRepository,
) : LoadCharactersByLocationIdUseCase {

    override fun invoke(locationId: Int): Flow<List<Character>> =
        characterRepository.getCharactersByLocationId(locationId)
}