package pl.mankevich.dataapi.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

interface EpisodeRepository {

    suspend fun getEpisodesPageFlow(episodeFilter: EpisodeFilter): Flow<PagingData<Episode>>

    fun getEpisodeDetail(episodeId: Int): Flow<Episode>

    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>>
}