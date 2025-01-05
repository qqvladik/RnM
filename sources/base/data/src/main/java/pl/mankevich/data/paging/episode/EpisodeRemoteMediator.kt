package pl.mankevich.data.paging.episode

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.data.mapper.mapToEpisodeDto
import pl.mankevich.data.mapper.mapToEntity
import pl.mankevich.data.mapper.mapToQuery
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.databaseapi.entity.EpisodePageKeyEntity
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter
import pl.mankevich.remoteapi.api.EpisodeApi

class EpisodeRemoteMediator @AssistedInject constructor(
    private val transaction: Transaction,
    private val episodeDao: EpisodeDao,
    private val relationsDao: RelationsDao,
    private val episodeApi: EpisodeApi,
    @Assisted private val episodeFilter: EpisodeFilter
) : RemoteMediator<Int, Episode>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Episode>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    1
                    // It works bad when remoteKey > 1. It scrolls to the top and loads PREPEND data
                    // infinitely because Room's PagingSource has such weird behavior
//                    val remoteKey = state.anchorPosition?.let { position ->
//                        state.closestItemToPosition(position)?.let {
//                            episodeDao.getPageKey(it.id, filter.mapToFilterDto())
//                        }
//                    }
//                    remoteKey?.value ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = state.firstItemOrNull()?.let {
                        episodeDao.getPageKey(it.id, episodeFilter.mapToEntity())
                    }
                    remoteKey?.previousPageKey ?: return MediatorResult.Success(remoteKey != null)
                }

                LoadType.APPEND -> {
                    val remoteKey = state.lastItemOrNull()?.let {
                        episodeDao.getPageKey(it.id, episodeFilter.mapToEntity())
                    }
                    remoteKey?.nextPageKey ?: return MediatorResult.Success(remoteKey != null)
                }
            }

            val episodesListResponse = episodeApi.fetchEpisodesList(
                page = currentPage,
                filter = episodeFilter.mapToQuery()
            )
            val responseInfo = episodesListResponse.info

            val idPageKeys = episodesListResponse.episodesResponse.map {
                EpisodePageKeyEntity(
                    episodeId = it.id,
                    filter = episodeFilter.mapToEntity(),
                    value = currentPage,
                    previousPageKey = responseInfo.prev,
                    nextPageKey = responseInfo.next
                )
            }

            transaction {
                val episodes = episodesListResponse.episodesResponse.map { episodeResponse ->
                    relationsDao.insertEpisodeCharacters(episodeResponse.id, episodeResponse.characterIds)
                    episodeResponse.mapToEpisodeDto()
                }

                episodeDao.insertPageKeysList(idPageKeys)
                episodeDao.insertEpisodesList(episodes)
            }
            MediatorResult.Success(endOfPaginationReached = responseInfo.next == null)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}