package pl.mankevich.storageroom.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.storageroom.mapper.mapToEpisodeDto
import pl.mankevich.storageroom.mapper.mapToEpisodeEntity
import pl.mankevich.storageroom.room.dao.EpisodeRoomDao
import pl.mankevich.storageapi.dao.EpisodeDao
import pl.mankevich.storageapi.dto.EpisodeDto
import javax.inject.Inject

class EpisodeDaoImpl
@Inject constructor(
    private val episodeRoomDao: EpisodeRoomDao,
) : EpisodeDao {

    override suspend fun insertEpisodesList(episodes: List<EpisodeDto>) =
        episodeRoomDao.insertEpisodesList(episodes.map { it.mapToEpisodeEntity() })

    override suspend fun getEpisodeById(id: Int): EpisodeDto =
        episodeRoomDao.getEpisodeById(id).mapToEpisodeDto()

    override suspend fun getEpisodesList(): List<EpisodeDto> =
        episodeRoomDao.getEpisodesList(limit = 20, offset = 0).map { it.mapToEpisodeDto() } //TODO add refactor and filter

    override fun getEpisodesByIds(ids: List<Int>): Flow<List<EpisodeDto>> =
        episodeRoomDao.getEpisodesByIds(ids).map { list -> list.map { it.mapToEpisodeDto() } }
}

