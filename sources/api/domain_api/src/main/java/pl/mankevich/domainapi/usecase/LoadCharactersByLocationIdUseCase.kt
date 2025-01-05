package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character

interface LoadCharactersByLocationIdUseCase {

    fun invoke(locationId: Int): Flow<List<Character>>
}