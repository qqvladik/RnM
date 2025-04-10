package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.domainapi.result.CharacterDetailResult

interface LoadCharacterDetailUseCase {

    operator fun invoke(characterId: Int): Flow<CharacterDetailResult>
}