package pl.mankevich.dataapi.repository

import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode

interface EpisodeRepository {

    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>>
}