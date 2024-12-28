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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.mankevich.data.mapper.mapToCharacter
import pl.mankevich.data.mapper.mapToCharacterDto
import pl.mankevich.data.paging.character.CharacterPagingSourceCreator
import pl.mankevich.data.paging.character.CharacterRemoteMediatorCreator
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import pl.mankevich.remoteapi.api.CharacterApi
import pl.mankevich.databaseapi.dao.CharacterDao
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

class CharacterRepositoryImpl
@Inject constructor(
    private val characterApi: CharacterApi,
    private val characterDao: CharacterDao,
    private val characterPagingSourceCreator: CharacterPagingSourceCreator,
    private val characterRemoteMediatorCreator: CharacterRemoteMediatorCreator,
    private val networkManager: NetworkManager
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

    override fun getCharacterDetail(characterId: Int): Flow<Character> =
        flow {
            emit(Unit)

            try {
                val characterResponse = characterApi.fetchCharacterById(characterId)
                characterDao.insertCharacter(characterResponse.mapToCharacterDto())
            } catch (_: Exception) {
                Log.e("CharacterRepositoryImpl", "Error while fetching character detail") //TODO result.error
            }
        }.flatMapLatest {
            characterDao.getCharacterById(characterId)
                .distinctUntilChanged()
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
}
