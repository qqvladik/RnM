package pl.mankevich.domainapi.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

interface LoadEpisodesListUseCase {

    suspend operator fun invoke(episodeFilter: EpisodeFilter): Flow<PagingData<Episode>>
}