package pl.mankevich.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pl.mankevich.data.paging.character.CharacterPagingSourceCreator
import pl.mankevich.data.paging.character.CharacterRemoteMediatorCreator
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.model.Character
import pl.mankevich.model.Filter
import pl.mankevich.storageapi.dao.CharacterDao
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

class CharacterRepositoryImpl
@Inject constructor(
    private val characterDao: CharacterDao,
    private val characterPagingSourceCreator: CharacterPagingSourceCreator,
    private val characterRemoteMediatorFactory: CharacterRemoteMediatorCreator
) : CharacterRepository {

    private lateinit var onTableUpdateListener: () -> Unit

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharactersPageFlow(isOnline: Boolean, filter: Filter): Flow<PagingData<Character>> {
        val characterPagingSourceFactory = createPagingSourceFactory {
            characterPagingSourceCreator.create(isOnline, filter)
        }

        val pager = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE * 2,
                maxSize = 200
            ),
            remoteMediator = characterRemoteMediatorFactory.create(isOnline, filter),
            pagingSourceFactory = characterPagingSourceFactory,
        )

        return pager.flow.flowOn(Dispatchers.IO)
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
