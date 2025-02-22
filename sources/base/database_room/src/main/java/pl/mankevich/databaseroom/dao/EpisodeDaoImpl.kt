package pl.mankevich.databaseroom.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.entity.EpisodeEntity
import pl.mankevich.databaseapi.entity.EpisodeFilterEntity
import pl.mankevich.databaseapi.entity.EpisodePageKeyEntity
import pl.mankevich.databaseroom.mapper.mapToEntity
import pl.mankevich.databaseroom.mapper.mapToRoom
import pl.mankevich.databaseroom.room.dao.EpisodePageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.EpisodeRoomDao
import javax.inject.Inject
import kotlin.collections.map

class EpisodeDaoImpl
@Inject constructor(
    private val episodeRoomDao: EpisodeRoomDao,
    private val episodePageKeyRoomDao: EpisodePageKeyRoomDao
) : EpisodeDao() {

    override suspend fun insertEpisode(episode: EpisodeEntity) {
        episodeRoomDao.insertEpisode(episode.mapToRoom())
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun insertEpisodesList(episodes: List<EpisodeEntity>) {
        episodeRoomDao.insertEpisodesList(episodes.map { it.mapToRoom() })
        tableUpdateNotifier.notifyListeners()
    }

    override fun getEpisodeById(id: Int): Flow<EpisodeEntity?> =
        episodeRoomDao.getEpisodeById(id).map { it?.mapToEntity() }

    override suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeEntity> =
        episodeRoomDao.getEpisodesByIds(ids).map { it.mapToEntity() }

    override fun getEpisodesFlowByIds(ids: List<Int>): Flow<List<EpisodeEntity>> =
        episodeRoomDao.getEpisodesFlowByIds(ids).map { list ->
            list.map { it.mapToEntity() }
        }

    override suspend fun getEpisodesList(
        episodeFilterEntity: EpisodeFilterEntity,
        limit: Int,
        offset: Int
    ): List<EpisodeEntity> =
        episodeRoomDao.getEpisodesList(
            name = episodeFilterEntity.name,
            episode = episodeFilterEntity.episode,
            limit = limit,
            offset = offset
        ).map { it.mapToEntity() }

    override suspend fun getEpisodesCount(episodeFilterEntity: EpisodeFilterEntity): Int =
        episodeRoomDao.getCount(
            name = episodeFilterEntity.name,
            episode = episodeFilterEntity.episode
        )

    override suspend fun getEpisodeIds(
        episodeFilterEntity: EpisodeFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int> =
        episodePageKeyRoomDao.getEpisodeIdsByFilter(episodeFilterEntity, limit, offset)

    override suspend fun insertPageKeysList(pageKeys: List<EpisodePageKeyEntity>) {
        episodePageKeyRoomDao.insertPageKeysList(
            pageKeys.map { it.mapToRoom() }
        )
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun getPageKey(
        characterId: Int,
        episodeFilterEntity: EpisodeFilterEntity
    ): EpisodePageKeyEntity? =
        episodePageKeyRoomDao.getPageKey(characterId, episodeFilterEntity)?.mapToEntity()

    override suspend fun getPageKeysCount(episodeFilterEntity: EpisodeFilterEntity): Int =
        episodePageKeyRoomDao.getCount(episodeFilterEntity)
}

