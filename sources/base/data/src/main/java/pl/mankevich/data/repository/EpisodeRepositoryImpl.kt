package pl.mankevich.data.repository

import android.util.Log
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.mankevich.core.di.Dispatcher
import pl.mankevich.core.di.RnmDispatchers.IO
import pl.mankevich.data.mapper.mapToEpisode
import pl.mankevich.data.mapper.mapToEpisodeDto
import pl.mankevich.data.paging.episode.EpisodePagingSourceCreator
import pl.mankevich.data.paging.episode.EpisodeRemoteMediatorCreator
import pl.mankevich.dataapi.dto.EpisodesResultDto
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter
import pl.mankevich.remoteapi.api.EpisodeApi
import java.net.UnknownHostException
import javax.inject.Inject

class EpisodeRepositoryImpl
@Inject constructor(
    private val episodeApi: EpisodeApi,
    private val episodeDao: EpisodeDao,
    private val relationsDao: RelationsDao,
    private val transaction: Transaction,
    private val episodePagingSourceCreator: EpisodePagingSourceCreator,
    private val episodeRemoteMediatorCreator: EpisodeRemoteMediatorCreator,
    private val networkManager: NetworkManager,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : EpisodeRepository {

    private lateinit var onTableUpdateListener: () -> Unit

    override suspend fun getEpisodesPageFlow(episodeFilter: EpisodeFilter): EpisodesResultDto {
        val isOnline = networkManager.isOnline()
        val episodePagingSourceFactory = createPagingSourceFactory {
            episodePagingSourceCreator.create(isOnline, episodeFilter)
        }

        val pager = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE * 2,
                maxSize = 200
            ),
            remoteMediator = episodeRemoteMediatorCreator.create(isOnline, episodeFilter),
            pagingSourceFactory = episodePagingSourceFactory,
        )

        return EpisodesResultDto(
            isOnline = isOnline,
            episodes = pager.flow.flowOn(dispatcher)
        )
    }

    override fun getEpisodeDetail(episodeId: Int): Flow<Episode> =
        flow {
            emit(Unit)

            try {
                val episodeResponse = episodeApi.fetchEpisodeById(episodeId)
                episodeDao.insertEpisode(episodeResponse.mapToEpisodeDto())
                relationsDao.insertEpisodeCharacters(episodeId, episodeResponse.characterIds)
            } catch (e: UnknownHostException) {
                Log.w("EpisodeRepositoryImpl", "getEpisodeDetail: $e")
            }
        }.flatMapLatest {
            episodeDao.getEpisodeById(episodeId)
                .distinctUntilChanged()
                .filterNotNull()
                .map {
                    it.mapToEpisode()
                }
        }.flowOn(dispatcher)

    private fun createPagingSourceFactory(
        pagingSourceFactory: () -> PagingSource<Int, Episode>
    ): PagingSourceFactory<Int, Episode> {
        val invalidatingPagingSourceFactory =
            InvalidatingPagingSourceFactory(pagingSourceFactory)

        onTableUpdateListener = { invalidatingPagingSourceFactory.invalidate() }
        episodeDao.addTableUpdateWeakListener(onTableUpdateListener)

        return invalidatingPagingSourceFactory
    }

    override fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>> {
        val episodeIdsFlow = relationsDao.getEpisodeIdsByCharacterId(characterId)
        return episodeIdsFlow
            .distinctUntilChanged()
            .debounce(QUERY_DELAY_MILLIS)
            .flatMapLatest { episodeIds ->
                if (episodeIds.isNotEmpty()) {
                    try {
                        val episodesListResponse = episodeApi.fetchEpisodesByIds(episodeIds)
                        transaction {
                            episodeDao.insertEpisodesList(episodesListResponse.map { it.mapToEpisodeDto() })
                            episodesListResponse.forEach { episodeResponse ->
                                relationsDao.insertEpisodeCharacters(
                                    episodeResponse.id,
                                    episodeResponse.characterIds
                                )
                            }
                        }
                    } catch (e: UnknownHostException) {
                        // TODO use ConnectionManager in future and remove this error handling.
                        // Avoids throw of error if there is no internet access
                        Log.w("EpisodeRepositoryImpl", "getEpisodesByCharacterId: $e")
                    }
                }

                episodeDao.getEpisodesFlowByIds(episodeIds)
                    .distinctUntilChanged()
                    .map { list -> list.map { it.mapToEpisode() } }
            }.flowOn(dispatcher)
    }
}
