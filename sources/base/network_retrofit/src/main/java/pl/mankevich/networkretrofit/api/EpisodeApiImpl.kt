package pl.mankevich.networkretrofit.api

import pl.mankevich.networkretrofit.retrofit.RnmApi
import pl.mankevich.networkretrofit.retrofit.response.EpisodeResponse
import pl.mankevich.networkretrofit.retrofit.response.EpisodesListResponse
import pl.mankevich.networkapi.api.EpisodeApi
import pl.mankevich.networkapi.dto.EpisodeResponseDto
import pl.mankevich.networkapi.dto.EpisodesListResponseDto
import javax.inject.Inject

class EpisodeApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : EpisodeApi {

    override suspend fun getEpisodeById(id: Int): EpisodeResponseDto =
        rnmApi.fetchSingleEpisode(id).mapToEpisodeDto()

    override suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeResponseDto> =
        rnmApi.fetchMultipleEpisodes(ids).map { it.mapToEpisodeDto() }

    override suspend fun getEpisodesList(): EpisodesListResponseDto =
        rnmApi.fetchAllEpisodes().mapToEpisodesListResponseDto()
}

private fun EpisodesListResponse.mapToEpisodesListResponseDto() = EpisodesListResponseDto(
    info = info.mapToInfoResponseDto(),
    episodesResponse = episodesResponse.map { it.mapToEpisodeDto() }
)

private fun EpisodeResponse.mapToEpisodeDto() = EpisodeResponseDto(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characterIds = characters.map { it.obtainId()!! }
)