package pl.mankevich.network.datasource

import pl.mankevich.network.api.RnmApi
import pl.mankevich.network.api.response.EpisodeResponse
import pl.mankevich.networkapi.datasource.EpisodeNetworkDataSource
import pl.mankevich.networkapi.dto.EpisodeResponseDto
import javax.inject.Inject

class EpisodeNetworkDataSourceImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : EpisodeNetworkDataSource {

    override suspend fun getEpisodeById(id: Int): EpisodeResponseDto =
        rnmApi.fetchSingleEpisode(id).mapToEpisodeDto()

    override suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeResponseDto> =
        rnmApi.fetchMultipleEpisodes(ids).map { it.mapToEpisodeDto() }

    override suspend fun getEpisodesList(): List<EpisodeResponseDto> =
        rnmApi.fetchAllEpisodes().episodesResponse.map { it.mapToEpisodeDto() }
}

private fun EpisodeResponse.mapToEpisodeDto() = EpisodeResponseDto(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characterIds = characters.map { it.obtainId() }
)