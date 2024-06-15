package pl.mankevich.core.datasource

import pl.mankevich.core.dto.Episode

interface EpisodeDataSource {

    suspend fun insertEpisodesList(episodes: List<Episode>)

    suspend fun getEpisodeById(id: Int): Episode?

    suspend fun getEpisodesByIds(ids: List<Int>): List<Episode>

    suspend fun getEpisodesList(): List<Episode>
}