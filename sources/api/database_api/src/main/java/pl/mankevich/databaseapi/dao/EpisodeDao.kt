package pl.mankevich.databaseapi.dao

import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseapi.entity.EpisodeEntity

interface EpisodeDao {

    suspend fun insertEpisodesList(episodes: List<EpisodeEntity>)

    suspend fun getEpisodeById(id: Int): EpisodeEntity?

    suspend fun getEpisodesList(): List<EpisodeEntity>

    fun getEpisodesByIds(ids: List<Int>): Flow<List<EpisodeEntity>>
}