package pl.mankevich.storage.datasource

import pl.mankevich.storageapi.dto.EpisodeDto
import pl.mankevich.storage.db.dao.EpisodeDao
import pl.mankevich.storage.db.entity.EpisodeEntity
import pl.mankevich.storageapi.datasource.EpisodeStorageDataSource
import javax.inject.Inject

class EpisodeStorageDataSourceImpl
@Inject constructor(
    private val episodeDao: EpisodeDao
) : EpisodeStorageDataSource {
    override suspend fun insertEpisodesList(episodes: List<EpisodeDto>) =
        episodeDao.insertEpisodesList(episodes.map { it.mapToEpisodeEntity() })

    override suspend fun getEpisodeById(id: Int): EpisodeDto =
        episodeDao.getEpisodeById(id).mapToEpisodeDto()

    override suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeDto> =
        episodeDao.getEpisodesByIds(ids).map { it.mapToEpisodeDto() }

    override suspend fun getEpisodesList(): List<EpisodeDto> =
        episodeDao.getEpisodesList().map { it.mapToEpisodeDto() }
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