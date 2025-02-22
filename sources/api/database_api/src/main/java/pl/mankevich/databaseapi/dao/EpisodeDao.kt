package pl.mankevich.databaseapi.dao

import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseapi.entity.EpisodeEntity
import pl.mankevich.databaseapi.entity.EpisodeFilterEntity
import pl.mankevich.databaseapi.entity.EpisodePageKeyEntity

abstract class EpisodeDao : DaoBase() {

    abstract suspend fun insertEpisode(episode: EpisodeEntity)

    abstract suspend fun insertEpisodesList(episodes: List<EpisodeEntity>)

    abstract fun getEpisodeById(id: Int): Flow<EpisodeEntity?>

    abstract suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeEntity>

    abstract fun getEpisodesFlowByIds(ids: List<Int>): Flow<List<EpisodeEntity>>

    abstract suspend fun getEpisodesList(
        episodeFilterEntity: EpisodeFilterEntity,
        limit: Int,
        offset: Int
    ): List<EpisodeEntity>

    abstract suspend fun getEpisodesCount(episodeFilterEntity: EpisodeFilterEntity): Int

    abstract suspend fun getEpisodeIds(
        episodeFilterEntity: EpisodeFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int>

    abstract suspend fun insertPageKeysList(pageKeys: List<EpisodePageKeyEntity>)

    abstract suspend fun getPageKey(
        characterId: Int,
        episodeFilterEntity: EpisodeFilterEntity
    ): EpisodePageKeyEntity?

    abstract suspend fun getPageKeysCount(episodeFilterEntity: EpisodeFilterEntity): Int
}