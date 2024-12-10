package pl.mankevich.remoteretrofit.api

import pl.mankevich.remoteretrofit.retrofit.RnmApi
import pl.mankevich.remoteretrofit.retrofit.response.EpisodeRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.EpisodesListRetrofitResponse
import pl.mankevich.remoteapi.api.EpisodeApi
import pl.mankevich.remoteapi.response.EpisodeResponse
import pl.mankevich.remoteapi.response.EpisodesListResponse
import javax.inject.Inject

class EpisodeApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : EpisodeApi {

    override suspend fun getEpisodeById(id: Int): EpisodeResponse =
        rnmApi.fetchSingleEpisode(id).mapToEpisodeDto()

    override suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeResponse> =
        rnmApi.fetchMultipleEpisodes(ids).map { it.mapToEpisodeDto() }

    override suspend fun getEpisodesList(): EpisodesListResponse =
        rnmApi.fetchAllEpisodes().mapToEpisodesListResponseDto()
}

private fun EpisodesListRetrofitResponse.mapToEpisodesListResponseDto() = EpisodesListResponse(
    info = info.mapToInfoResponseDto(),
    episodesResponse = episodesResponse.map { it.mapToEpisodeDto() }
)

private fun EpisodeRetrofitResponse.mapToEpisodeDto() = EpisodeResponse(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characterIds = characters.map { it.obtainId()!! }
)