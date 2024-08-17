package pl.mankevich.storageapi.dao

import pl.mankevich.storageapi.dto.EpisodeDto

interface EpisodeDao {

    suspend fun insertEpisodesList(episodes: List<EpisodeDto>)

    suspend fun getEpisodeById(id: Int): EpisodeDto?

    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeDto>

    suspend fun getEpisodesList(): List<EpisodeDto>
}