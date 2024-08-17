package pl.mankevich.storageapi.dao

import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.CharacterPageKeyDto
import pl.mankevich.storageapi.dto.FilterDto

abstract class CharacterDao : DaoBase() {

    abstract suspend fun insertCharactersAndPageKeys( //TODO remove in future
        characters: List<CharacterDto>,
        pageKeys: List<CharacterPageKeyDto>
    )

    abstract suspend fun insertCharactersList(characters: List<CharacterDto>)

    abstract suspend fun getCharacterById(id: Int): CharacterDto?

    abstract suspend fun getCharactersByIds(ids: List<Int>): List<CharacterDto>

    abstract suspend fun getCharactersList(
        filterDto: FilterDto,
        limit: Int,
        offset: Int
    ): List<CharacterDto>

    abstract suspend fun getCharactersCount(filterDto: FilterDto): Int

    abstract suspend fun insertPageKeysList(pageKeys: List<CharacterPageKeyDto>)

    abstract suspend fun getCharacterIdsByFilter(
        filterDto: FilterDto,
        limit: Int,
        offset: Int
    ): List<Int>

    abstract suspend fun getPageKey(characterId: Int, filterDto: FilterDto): CharacterPageKeyDto?

    abstract suspend fun getPageKeysCount(filterDto: FilterDto): Int
}