package pl.mankevich.dataapi.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter

interface CharacterRepository {

    suspend fun getCharactersPageFlow(characterFilter: CharacterFilter): Flow<PagingData<Character>>

    fun getCharacterDetail(characterId: Int): Flow<Character>

    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<Character>>

    fun getCharactersByLocationId(locationId: Int): Flow<List<Character>>
}