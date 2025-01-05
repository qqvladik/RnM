package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character

interface LoadCharactersByEpisodeIdUseCase {

    fun invoke(episodeId: Int): Flow<List<Character>>
}