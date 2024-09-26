package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character

interface LoadCharacterDetailUseCase {

    operator fun invoke(characterId: Int): Flow<Character>
}