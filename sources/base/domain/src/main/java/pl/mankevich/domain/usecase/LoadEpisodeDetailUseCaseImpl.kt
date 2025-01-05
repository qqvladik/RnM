package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.domainapi.usecase.LoadEpisodeDetailUseCase
import pl.mankevich.model.Episode
import javax.inject.Inject

class LoadEpisodeDetailUseCaseImpl
@Inject constructor(
    private val episodeRepository: EpisodeRepository
) : LoadEpisodeDetailUseCase {

    override operator fun invoke(episodeId: Int): Flow<Episode> =
        episodeRepository.getEpisodeDetail(episodeId)
}