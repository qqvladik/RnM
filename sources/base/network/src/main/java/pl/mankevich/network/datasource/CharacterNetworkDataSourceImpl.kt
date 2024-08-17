package pl.mankevich.network.datasource

import pl.mankevich.network.api.RnmApi
import pl.mankevich.network.api.response.CharacterResponse
import pl.mankevich.network.api.response.CharactersListResponse
import pl.mankevich.network.api.response.LocationShortResponse
import pl.mankevich.networkapi.datasource.CharacterNetworkDataSource
import pl.mankevich.networkapi.dto.CharacterResponseDto
import pl.mankevich.networkapi.dto.CharactersListResponseDto
import pl.mankevich.networkapi.dto.FilterQueryDto
import pl.mankevich.networkapi.dto.LocationShortResponseDto
import javax.inject.Inject

class CharacterNetworkDataSourceImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : CharacterNetworkDataSource {

    override suspend fun fetchCharacterById(id: Int): CharacterResponseDto =
        rnmApi.fetchSingleCharacter(id).mapToCharacterDto()

    override suspend fun fetchCharactersByIds(ids: List<Int>): List<CharacterResponseDto> =
        rnmApi.fetchMultipleCharacters(ids).map { it.mapToCharacterDto() }

    override suspend fun fetchCharactersList(
        page: Int,
        filter: FilterQueryDto
    ): CharactersListResponseDto = rnmApi.fetchAllCharacters(
        page = page,
        name = filter.name,
        status = filter.status,
        species = filter.species,
        type = filter.type,
        gender = filter.gender
    ).mapToCharactersListResponseDto()
}

private fun CharactersListResponse.mapToCharactersListResponseDto() = CharactersListResponseDto(
    info = info.mapToInfoResponseDto(),
    charactersResponse = charactersResponse.map { it.mapToCharacterDto() }
)

private fun CharacterResponse.mapToCharacterDto() = CharacterResponseDto(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.mapToLocationShortDto(),
    location = location.mapToLocationShortDto(),
    image = image,
    episodeIds = episode.map { it.obtainId()!! }
)

private fun LocationShortResponse.mapToLocationShortDto() = LocationShortResponseDto(
    id = url.obtainId(),
    name = name,
)