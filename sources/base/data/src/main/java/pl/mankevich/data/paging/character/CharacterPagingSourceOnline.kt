package pl.mankevich.data.paging.character

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.data.mapper.mapToCharacter
import pl.mankevich.data.mapper.mapToFilterDto
import pl.mankevich.model.Character
import pl.mankevich.model.Filter
import pl.mankevich.storageapi.dao.CharacterDao
import pl.mankevich.storageapi.dao.Transaction

class CharacterPagingSourceOnline @AssistedInject constructor(
    private val characterDao: CharacterDao,
    private val transaction: Transaction,
    @Assisted private val filter: Filter,
) : PagingSource<Character>() {

    override suspend fun getCount(): Int {
        return characterDao.getPageKeysCount(filter.mapToFilterDto())
    }

    override suspend fun getData(limit: Int, offset: Int): List<Character> {
        val characterIds = characterDao.getCharacterIdsByFilter(
            filter.mapToFilterDto(),
            limit,
            offset
        )
        return characterDao.getCharactersByIds(characterIds).map { it.mapToCharacter() }
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        transaction(block)
}
