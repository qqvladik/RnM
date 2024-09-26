package pl.mankevich.storageapi.dao

import kotlinx.coroutines.flow.Flow
import pl.mankevich.storageapi.dto.EpisodeDto

interface EpisodeDao {

    suspend fun insertEpisodesList(episodes: List<EpisodeDto>)

    suspend fun getEpisodeById(id: Int): EpisodeDto?

    suspend fun getEpisodesList(): List<EpisodeDto>

    fun getEpisodesByIds(ids: List<Int>): Flow<List<EpisodeDto>>
}