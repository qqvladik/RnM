package pl.mankevich.dataapi.repository

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.dto.EpisodeDetailResultDto
import pl.mankevich.dataapi.dto.EpisodesResultDto
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

interface EpisodeRepository {

    suspend fun getEpisodesPageFlow(episodeFilter: EpisodeFilter): EpisodesResultDto

    fun getEpisodeDetail(episodeId: Int): Flow<EpisodeDetailResultDto>

    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>>
}