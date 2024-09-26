package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode

interface LoadEpisodesByCharacterIdUseCase {

    operator fun invoke(characterId: Int): Flow<List<Episode>>
}