package pl.mankevich.storageapi.datasource

import pl.mankevich.storageapi.dto.CharacterDto

interface CharacterStorageDataSource {

    suspend fun insertCharactersList(characters: List<CharacterDto>)

    suspend fun getCharacterById(id: Int): CharacterDto?

    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterDto>

    suspend fun getCharactersList(): List<CharacterDto>
}