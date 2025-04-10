package pl.mankevich.domainapi.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.domainapi.result.EpisodeDetailResult

interface LoadEpisodeDetailUseCase {

    operator fun invoke(episodeId: Int): Flow<EpisodeDetailResult>
}