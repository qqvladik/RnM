package pl.mankevich.core.datasource

import pl.mankevich.core.dto.Character

interface CharacterDataSource {

    suspend fun insertCharactersList(characters: List<Character>)

    suspend fun getCharacterById(id: Int): Character?

    suspend fun getCharactersByIds(ids: List<Int>): List<Character>

    suspend fun getCharactersList(): List<Character>
}