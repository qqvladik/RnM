package pl.mankevich.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import pl.mankevich.data.mapper.mapToEpisode
import pl.mankevich.data.mapper.mapToEpisodeDto
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.model.Episode
import pl.mankevich.remoteapi.api.EpisodeApi
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction
import javax.inject.Inject

private const val QUERY_DELAY_MILLIS = 1000L

class EpisodeRepositoryImpl
@Inject constructor(
    private val episodeApi: EpisodeApi,
    private val episodeDao: EpisodeDao,
    private val relationsDao: RelationsDao,
    private val transaction: Transaction
) : EpisodeRepository {

    override fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>> {
        val episodeIdsFlow = relationsDao.getEpisodeIdsByCharacterId(characterId)
        return episodeIdsFlow
            .distinctUntilChanged()
            .debounce(QUERY_DELAY_MILLIS)
            .flatMapLatest { episodeIds ->
                flow {
                    emit(Unit)

                    try {
                        val episodesListResponse = episodeApi.getEpisodesByIds(episodeIds)
                        transaction {
                            episodeDao.insertEpisodesList(episodesListResponse.map { it.mapToEpisodeDto() })
                            episodesListResponse.forEach { episodeResponse ->
                                relationsDao.insertEpisodeCharacters(
                                    episodeResponse.id,
                                    episodeResponse.characterIds
                                )
                            }
                        }
                    } catch (_: Exception) {
                        Log.e("EpisodeRepositoryImpl", "Error while fetching Episodes By CharacterId") //TODO result.error
                    }
                }.flatMapLatest {
                    episodeDao.getEpisodesFlowByIds(episodeIds)
                        .distinctUntilChanged()
                        .map { list -> list.map { it.mapToEpisode() } }
                }
            }
    }
}
