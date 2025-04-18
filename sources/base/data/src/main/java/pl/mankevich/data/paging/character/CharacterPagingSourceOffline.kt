package pl.mankevich.data.paging.character

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.data.mapper.mapToCharacter
import pl.mankevich.data.mapper.mapToEntity
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import pl.mankevich.databaseapi.dao.CharacterDao
import pl.mankevich.databaseapi.dao.Transaction

class CharacterPagingSourceOffline @AssistedInject constructor(
    private val characterDao: CharacterDao,
    private val transaction: Transaction,
    @Assisted private val characterFilter: CharacterFilter,
) : PagingSource<Character>() {

    override suspend fun getCount(): Int {
        return characterDao.getCharactersCount(characterFilter.mapToEntity())
    }

    override suspend fun getData(limit: Int, offset: Int): List<Character> {
        return characterDao.getCharactersList(
            characterFilter.mapToEntity(),
            limit,
            offset
        ).map { it.mapToCharacter() }
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        transaction(block)
}
