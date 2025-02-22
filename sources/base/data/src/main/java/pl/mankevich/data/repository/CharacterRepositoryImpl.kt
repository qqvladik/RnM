package pl.mankevich.data.repository

import android.util.Log
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.mankevich.data.mapper.mapToCharacter
import pl.mankevich.data.mapper.mapToCharacterDto
import pl.mankevich.data.paging.character.CharacterPagingSourceCreator
import pl.mankevich.data.paging.character.CharacterRemoteMediatorCreator
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.databaseapi.dao.CharacterDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import pl.mankevich.remoteapi.api.CharacterApi
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.collections.map

class CharacterRepositoryImpl
@Inject constructor(
    private val characterApi: CharacterApi,
    private val characterDao: CharacterDao,
    private val relationsDao: RelationsDao,
    private val transaction: Transaction,
    private val characterPagingSourceCreator: CharacterPagingSourceCreator,
    private val characterRemoteMediatorCreator: CharacterRemoteMediatorCreator,
    private val networkManager: NetworkManager,
) : CharacterRepository {

    private lateinit var onTableUpdateListener: () -> Unit

    override suspend fun getCharactersPageFlow(characterFilter: CharacterFilter): Flow<PagingData<Character>> {
        val isOnline = networkManager.isOnline()
        val characterPagingSourceFactory = createPagingSourceFactory {
            characterPagingSourceCreator.create(isOnline, characterFilter)
        }

        val pager = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE * 2,
                maxSize = 200
            ),
            remoteMediator = characterRemoteMediatorCreator.create(isOnline, characterFilter),
            pagingSourceFactory = characterPagingSourceFactory,
        )

        return pager.flow.flowOn(Dispatchers.IO)
    }

    //TODO get coroutineScope/context as parameter, start Coroutine in this scope, then return flow, which observes database
    override fun getCharacterDetail(characterId: Int): Flow<Character> =
        flow {
            emit(Unit)
            try {
                val characterResponse = characterApi.fetchCharacterById(characterId)
                characterDao.insertCharacter(characterResponse.mapToCharacterDto())
            } catch (e: UnknownHostException) { //TODO custom errors or Result
                Log.w("CharacterRepositoryImpl", "getCharacterDetail: $e") //TODO tag
            }
        }.flatMapLatest {
            characterDao.getCharacterById(characterId)
                .distinctUntilChanged()
                .filterNotNull()
                .map {
                    it.mapToCharacter()
                }
        }

    private fun createPagingSourceFactory(
        pagingSourceFactory: () -> PagingSource<Int, Character>
    ): PagingSourceFactory<Int, Character> {
        val invalidatingPagingSourceFactory = InvalidatingPagingSourceFactory(pagingSourceFactory)

        onTableUpdateListener = { invalidatingPagingSourceFactory.invalidate() }
        characterDao.addTableUpdateWeakListener(onTableUpdateListener)

        return invalidatingPagingSourceFactory
    }

    override fun getCharactersByEpisodeId(episodeId: Int): Flow<List<Character>> {
        val characterIds = relationsDao.getCharacterIdsByEpisodeId(episodeId)
        return characterIds
            .distinctUntilChanged()
            .debounce(QUERY_DELAY_MILLIS)
            .flatMapLatest { characterIds ->
                flow {
                    emit(Unit)

                    try {
                        val charactersListResponse = characterApi.fetchCharactersByIds(characterIds)
                        transaction {
                            characterDao.insertCharactersList(charactersListResponse.map { it.mapToCharacterDto() })
                            charactersListResponse.forEach { characterResponse ->
                                relationsDao.insertEpisodeCharacters(
                                    characterResponse.id,
                                    characterResponse.episodeIds
                                )
                            }
                        }
                    } catch (e: UnknownHostException) {
                        Log.w("CharacterRepositoryImpl", "getCharactersByEpisodeId: $e")
                    }
                }.flatMapLatest {
                    characterDao.getCharactersFlowByIds(characterIds)
                        .distinctUntilChanged()
                        .map { list -> list.map { it.mapToCharacter() } }
                }
            }
    }

    override fun getCharactersByLocationId(locationId: Int): Flow<List<Character>> {
        val episodeIdsFlow = relationsDao.getCharacterIdsByLocationId(locationId)
        return episodeIdsFlow
            .distinctUntilChanged()
            .debounce(QUERY_DELAY_MILLIS)
            .flatMapLatest { characterIds ->
                flow {
                    emit(Unit)

                    try {
                        val charactersListResponse = characterApi.fetchCharactersByIds(characterIds)
                        transaction {
                            characterDao.insertCharactersList(charactersListResponse.map { it.mapToCharacterDto() })
                            charactersListResponse.forEach { characterResponse ->
                                relationsDao.insertEpisodeCharacters(
                                    characterResponse.id,
                                    characterResponse.episodeIds
                                )
                            }
                        }
                    } catch (e: UnknownHostException) {
                        Log.w("CharacterRepositoryImpl", "getCharactersByLocationId: $e")
                    }
                }.flatMapLatest {
                    characterDao.getCharactersFlowByIds(characterIds)
                        .distinctUntilChanged()
                        .map { list -> list.map { it.mapToCharacter() } }
                }
            }
    }
}
