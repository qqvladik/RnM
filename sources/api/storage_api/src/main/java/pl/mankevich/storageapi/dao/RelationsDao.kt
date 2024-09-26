package pl.mankevich.storageapi.dao

import kotlinx.coroutines.flow.Flow

interface RelationsDao {

    fun insertCharacterEpisodes(characterId: Int, episodeIds: List<Int>)

    fun insertLocationCharacters(locationId: Int, characterIds: List<Int>)

    fun insertEpisodeCharacters(episodeId: Int, characterIds: List<Int>)

    fun getEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>>
}