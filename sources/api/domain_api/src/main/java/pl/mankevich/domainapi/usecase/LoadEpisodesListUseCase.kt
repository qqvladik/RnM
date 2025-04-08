package pl.mankevich.domainapi.usecase

import pl.mankevich.domainapi.result.EpisodesResult
import pl.mankevich.model.EpisodeFilter

interface LoadEpisodesListUseCase {

    suspend operator fun invoke(episodeFilter: EpisodeFilter): EpisodesResult
}