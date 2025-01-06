package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character

interface LoadCharactersByEpisodeIdUseCase {

    operator fun invoke(episodeId: Int): Flow<List<Character>>
}