package pl.mankevich.storage.dao

import pl.mankevich.storage.room.dao.EpisodeRoomDao
import pl.mankevich.storage.room.entity.EpisodeEntity
import pl.mankevich.storageapi.dao.EpisodeDao
import pl.mankevich.storageapi.dto.EpisodeDto
import javax.inject.Inject

class EpisodeDaoImpl
@Inject constructor(
    private val episodeRoomDao: EpisodeRoomDao
) : EpisodeDao {
    override suspend fun insertEpisodesList(episodes: List<EpisodeDto>) =
        episodeRoomDao.insertEpisodesList(episodes.map { it.mapToEpisodeEntity() })

    override suspend fun getEpisodeById(id: Int): EpisodeDto =
        episodeRoomDao.getEpisodeById(id).mapToEpisodeDto()

    override suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeDto> =
        episodeRoomDao.getEpisodesByIds(ids).map { it.mapToEpisodeDto() }

    override suspend fun getEpisodesList(): List<EpisodeDto> =
        episodeRoomDao.getEpisodesList().map { it.mapToEpisodeDto() }
}

private fun EpisodeDto.mapToEpisodeEntity() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

private fun EpisodeEntity.mapToEpisodeDto() = EpisodeDto(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)