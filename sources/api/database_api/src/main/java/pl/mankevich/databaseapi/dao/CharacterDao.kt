package pl.mankevich.databaseapi.dao

import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseapi.entity.CharacterEntity
import pl.mankevich.databaseapi.entity.CharacterPageKeyEntity
import pl.mankevich.databaseapi.entity.CharacterFilterEntity

abstract class CharacterDao : DaoBase() {

    abstract suspend fun insertCharacter(character: CharacterEntity)

    abstract suspend fun insertCharactersList(characters: List<CharacterEntity>)

    abstract fun getCharacterById(id: Int): Flow<CharacterEntity>

    abstract suspend fun getCharactersByIds(ids: List<Int>): List<CharacterEntity>

    abstract fun getCharactersFlowByIds(ids: List<Int>): Flow<List<CharacterEntity>>

    abstract suspend fun getCharactersList(
        characterFilterEntity: CharacterFilterEntity,
        limit: Int,
        offset: Int
    ): List<CharacterEntity>

    abstract suspend fun getCharactersCount(characterFilterEntity: CharacterFilterEntity): Int

    abstract suspend fun getCharacterIds(
        characterFilterEntity: CharacterFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int>

    abstract suspend fun insertPageKeysList(pageKeys: List<CharacterPageKeyEntity>)

    abstract suspend fun getPageKey(characterId: Int, characterFilterEntity: CharacterFilterEntity): CharacterPageKeyEntity?

    abstract suspend fun getPageKeysCount(characterFilterEntity: CharacterFilterEntity): Int
}