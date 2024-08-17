package pl.mankevich.networkapi.datasource

import pl.mankevich.networkapi.dto.EpisodeResponseDto
import pl.mankevich.networkapi.dto.EpisodesListResponseDto

interface EpisodeNetworkDataSource {

    suspend fun getEpisodeById(id: Int): EpisodeResponseDto

    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeResponseDto>

    suspend fun getEpisodesList(): EpisodesListResponseDto
}