package pl.mankevich.storage.db.datasource

import pl.mankevich.core.datasource.EpisodeDataSource
import pl.mankevich.core.dto.Episode
import pl.mankevich.storage.db.dao.EpisodeDao
import pl.mankevich.storage.db.entity.EpisodeEntity
import javax.inject.Inject

class EpisodeDataSourceImpl
@Inject constructor(
    private val episodeDao: EpisodeDao
) : EpisodeDataSource {
    override suspend fun insertEpisodesList(episodes: List<Episode>) =
        episodeDao.insertEpisodesList(episodes.map { it.mapToEpisodeEntity() })

    override suspend fun getEpisodeById(id: Int): Episode? =
        episodeDao.getEpisodeById(id)?.mapToEpisode()

    override suspend fun getEpisodesByIds(ids: List<Int>): List<Episode> =
        episodeDao.getEpisodesByIds(ids).map { it.mapToEpisode() }

    override suspend fun getEpisodesList(): List<Episode> =
        episodeDao.getEpisodesList().map { it.mapToEpisode() }
}

private fun Episode.mapToEpisodeEntity() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

private fun EpisodeEntity.mapToEpisode() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)