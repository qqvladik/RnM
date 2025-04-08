package pl.mankevich.dataapi.repository

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.dto.CharactersResultDto
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter

interface CharacterRepository {

    suspend fun getCharactersPageFlow(characterFilter: CharacterFilter): CharactersResultDto

    fun getCharacterDetail(characterId: Int): Flow<Character>

    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<Character>>

    fun getCharactersByLocationId(locationId: Int): Flow<List<Character>>
}