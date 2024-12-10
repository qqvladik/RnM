package pl.mankevich.databaseroom.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.databaseroom.mapper.mapToEntity
import pl.mankevich.databaseroom.mapper.mapToRoom
import pl.mankevich.databaseroom.room.dao.EpisodeRoomDao
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.entity.EpisodeEntity
import javax.inject.Inject

class EpisodeDaoImpl
@Inject constructor(
    private val episodeRoomDao: EpisodeRoomDao,
) : EpisodeDao {

    override suspend fun insertEpisodesList(episodes: List<EpisodeEntity>) =
        episodeRoomDao.insertEpisodesList(episodes.map { it.mapToRoom() })

    override suspend fun getEpisodeById(id: Int): EpisodeEntity =
        episodeRoomDao.getEpisodeById(id).mapToEntity()

    override suspend fun getEpisodesList(): List<EpisodeEntity> =
        episodeRoomDao.getEpisodesList(limit = 20, offset = 0).map { it.mapToEntity() } //TODO add refactor and filter

    override fun getEpisodesByIds(ids: List<Int>): Flow<List<EpisodeEntity>> =
        episodeRoomDao.getEpisodesByIds(ids).map { list -> list.map { it.mapToEntity() } }
}

