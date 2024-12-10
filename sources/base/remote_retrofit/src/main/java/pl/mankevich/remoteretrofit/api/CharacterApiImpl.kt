package pl.mankevich.remoteretrofit.api

import pl.mankevich.remoteretrofit.retrofit.RnmApi
import pl.mankevich.remoteretrofit.retrofit.response.CharacterRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.CharactersListRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.LocationShortRetrofitResponse
import pl.mankevich.remoteapi.api.CharacterApi
import pl.mankevich.remoteapi.response.CharacterResponse
import pl.mankevich.remoteapi.response.CharactersListResponse
import pl.mankevich.remoteapi.query.CharacterFilterQuery
import pl.mankevich.remoteapi.response.InfoResponse
import pl.mankevich.remoteapi.response.LocationShortResponse
import javax.inject.Inject

class CharacterApiImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : CharacterApi {

    override suspend fun fetchCharacterById(id: Int): CharacterResponse =
        rnmApi.fetchSingleCharacter(id).mapToResponse()

    override suspend fun fetchCharactersByIds(ids: List<Int>): List<CharacterResponse> =
        rnmApi.fetchMultipleCharacters(ids).map { it.mapToResponse() }

    override suspend fun fetchCharactersList(
        page: Int,
        filter: CharacterFilterQuery
    ): CharactersListResponse = try {
        rnmApi.fetchAllCharacters(
            page = page,
            name = filter.name,
            status = filter.status,
            species = filter.species,
            type = filter.type,
            gender = filter.gender
        ).mapToResponse()
    } catch (e: retrofit2.HttpException) {
        if (e.code() == 404) {
            CharactersListResponse(
                info = InfoResponse(
                    count = 0,
                    pages = 0,
                    next = null,
                    prev = null
                ),
                charactersResponse = emptyList()
            )
        } else {
            e.printStackTrace()
            throw e
        }
    }
}

private fun CharactersListRetrofitResponse.mapToResponse() = CharactersListResponse(
    info = info.mapToInfoResponseDto(),
    charactersResponse = charactersResponse.map { it.mapToResponse() }
)

private fun CharacterRetrofitResponse.mapToResponse() = CharacterResponse(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.mapToResponse(),
    location = location.mapToResponse(),
    image = image,
    episodeIds = episode.map { it.obtainId()!! }
)

private fun LocationShortRetrofitResponse.mapToResponse() = LocationShortResponse(
    id = url.obtainId(),
    name = name,
)