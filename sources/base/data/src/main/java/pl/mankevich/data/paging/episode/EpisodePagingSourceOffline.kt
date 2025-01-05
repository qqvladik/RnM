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

class EpisodePagingSourceOffline @AssistedInject constructor(
    private val episodeDao: EpisodeDao,
    private val transaction: Transaction,
    @Assisted private val episodeFilter: EpisodeFilter,
) : PagingSource<Episode>() {

    override suspend fun getCount(): Int {
        return episodeDao.getEpisodesCount(episodeFilter.mapToEntity())
    }

    override suspend fun getData(limit: Int, offset: Int): List<Episode> {
        return episodeDao.getEpisodesList(
            episodeFilter.mapToEntity(),
            limit,
            offset
        ).map { it.mapToEpisode() }
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        transaction(block)
}
