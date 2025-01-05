package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode

interface LoadEpisodeDetailUseCase {

    operator fun invoke(episodeId: Int): Flow<Episode>
}