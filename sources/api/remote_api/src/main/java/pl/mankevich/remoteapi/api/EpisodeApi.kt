package pl.mankevich.remoteapi.api

import pl.mankevich.remoteapi.response.EpisodeResponse
import pl.mankevich.remoteapi.response.EpisodesListResponse

interface EpisodeApi {

    suspend fun getEpisodeById(id: Int): EpisodeResponse

    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeResponse>

    suspend fun getEpisodesList(): EpisodesListResponse
}