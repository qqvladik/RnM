package pl.mankevich.remoteapi.api

import pl.mankevich.remoteapi.query.EpisodeFilterQuery
import pl.mankevich.remoteapi.response.EpisodeResponse
import pl.mankevich.remoteapi.response.EpisodesListResponse

interface EpisodeApi {

    suspend fun fetchEpisodeById(id: Int): EpisodeResponse

    suspend fun fetchEpisodesByIds(ids: List<Int>): List<EpisodeResponse>

    suspend fun fetchEpisodesList(page: Int, filter: EpisodeFilterQuery): EpisodesListResponse
}