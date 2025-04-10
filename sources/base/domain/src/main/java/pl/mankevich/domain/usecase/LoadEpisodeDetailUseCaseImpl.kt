package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.dataapi.dto.EpisodeDetailResultDto
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.domainapi.result.EpisodeDetailResult
import pl.mankevich.domainapi.usecase.LoadEpisodeDetailUseCase
import javax.inject.Inject

class LoadEpisodeDetailUseCaseImpl
@Inject constructor(
    private val episodeRepository: EpisodeRepository
) : LoadEpisodeDetailUseCase {

    override operator fun invoke(episodeId: Int): Flow<EpisodeDetailResult> =
        episodeRepository.getEpisodeDetail(episodeId).map { it.mapToResult() }
}

fun EpisodeDetailResultDto.mapToResult(): EpisodeDetailResult =
    EpisodeDetailResult(
        isOnline = isOnline,
        episode = episode
    )