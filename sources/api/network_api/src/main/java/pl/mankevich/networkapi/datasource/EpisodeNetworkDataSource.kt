package pl.mankevich.networkapi.datasource

import pl.mankevich.networkapi.dto.EpisodeResponseDto

interface EpisodeNetworkDataSource {

    suspend fun getEpisodeById(id: Int): EpisodeResponseDto

    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeResponseDto>

    suspend fun getEpisodesList(): List<EpisodeResponseDto>
}