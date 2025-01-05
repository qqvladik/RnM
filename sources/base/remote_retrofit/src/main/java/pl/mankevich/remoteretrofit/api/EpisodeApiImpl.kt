package pl.mankevich.remoteretrofit.api

import pl.mankevich.remoteapi.api.EpisodeApi
import pl.mankevich.remoteapi.query.EpisodeFilterQuery
import pl.mankevich.remoteapi.response.EpisodeResponse
import pl.mankevich.remoteapi.response.EpisodesListResponse
import pl.mankevich.remoteapi.response.InfoResponse
import pl.mankevich.remoteretrofit.retrofit.RnmApi
import pl.mankevich.remoteretrofit.retrofit.response.EpisodeRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.EpisodesListRetrofitResponse
import javax.inject.Inject

class EpisodeApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : EpisodeApi {

    override suspend fun fetchEpisodeById(id: Int): EpisodeResponse =
        rnmApi.fetchSingleEpisode(id).mapToResponse()

    override suspend fun fetchEpisodesByIds(ids: List<Int>): List<EpisodeResponse> =
        rnmApi.fetchMultipleEpisodes(ids).map { it.mapToResponse() }

    override suspend fun fetchEpisodesList(
        page: Int,
        filter: EpisodeFilterQuery
    ): EpisodesListResponse = try {
        rnmApi.fetchAllEpisodes(
            page = page,
            name = filter.name,
            episode = filter.episode
        ).mapToResponse()
    } catch (e: retrofit2.HttpException) {
        if (e.code() == 404) {
            EpisodesListResponse(
                info = InfoResponse(
                    count = 0,
                    pages = 0,
                    next = null,
                    prev = null
                ),
                episodesResponse = emptyList()
            )
        } else {
            e.printStackTrace()
            throw e
        }
    }
}

private fun EpisodesListRetrofitResponse.mapToResponse() = EpisodesListResponse(
    info = info.mapToInfoResponseDto(),
    episodesResponse = episodesResponse.map { it.mapToResponse() }
)

private fun EpisodeRetrofitResponse.mapToResponse() = EpisodeResponse(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characterIds = characters.map { it.obtainId()!! }
)