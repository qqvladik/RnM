package pl.mankevich.data.paging.episode

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.data.mapper.mapToEntity
import pl.mankevich.data.mapper.mapToEpisode
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

class EpisodePagingSourceOnline @AssistedInject constructor(
    private val episodeDao: EpisodeDao,
    private val transaction: Transaction,
    @Assisted private val episodeFilter: EpisodeFilter,
) : PagingSource<Episode>() {

    override suspend fun getCount(): Int {
        return episodeDao.getPageKeysCount(episodeFilter.mapToEntity())
    }

    override suspend fun getData(limit: Int, offset: Int): List<Episode> {
        val episodeIds = episodeDao.getEpisodeIds(
            episodeFilter.mapToEntity(),
            limit,
            offset
        )
        return episodeDao.getEpisodesByIds(episodeIds).map { it.mapToEpisode() }
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        transaction(block)
}
