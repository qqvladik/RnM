package pl.mankevich.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.domainapi.usecase.LoadEpisodesListUseCase
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter
import javax.inject.Inject

class LoadEpisodesListUseCaseImpl
@Inject constructor(
    private val episodeRepository: EpisodeRepository
) : LoadEpisodesListUseCase {

    override suspend operator fun invoke(episodeFilter: EpisodeFilter): Flow<PagingData<Episode>> {
        return episodeRepository.getEpisodesPageFlow(episodeFilter)
    }
}