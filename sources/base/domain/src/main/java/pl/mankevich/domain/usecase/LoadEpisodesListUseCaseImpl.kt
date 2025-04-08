package pl.mankevich.domain.usecase

import pl.mankevich.dataapi.dto.EpisodesResultDto
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.domainapi.result.EpisodesResult
import pl.mankevich.domainapi.usecase.LoadEpisodesListUseCase
import pl.mankevich.model.EpisodeFilter
import javax.inject.Inject

class LoadEpisodesListUseCaseImpl
@Inject constructor(
    private val episodeRepository: EpisodeRepository
) : LoadEpisodesListUseCase {

    override suspend operator fun invoke(episodeFilter: EpisodeFilter): EpisodesResult {
        return episodeRepository.getEpisodesPageFlow(episodeFilter).mapToResult()
    }
}

fun EpisodesResultDto.mapToResult() = EpisodesResult(
    isOnline = isOnline,
    episodes = episodes
)