package pl.mankevich.network.datasource

import pl.mankevich.network.api.RnmApi
import pl.mankevich.network.api.response.CharacterResponse
import pl.mankevich.network.api.response.LocationShortResponse
import pl.mankevich.networkapi.datasource.CharacterNetworkDataSource
import pl.mankevich.networkapi.dto.CharacterResponseDto
import pl.mankevich.networkapi.dto.LocationShortResponseDto
import javax.inject.Inject

class CharacterNetworkDataSourceImpl
@Inject constructor(
    private val rnmApi: RnmApi
) : CharacterNetworkDataSource {

    override suspend fun getCharacterById(id: Int): CharacterResponseDto =
        rnmApi.fetchSingleCharacter(id).mapToCharacterDto()

    override suspend fun getCharactersByIds(ids: List<Int>): List<CharacterResponseDto> =
        rnmApi.fetchMultipleCharacters(ids).map { it.mapToCharacterDto() }

    override suspend fun getCharactersList(): List<CharacterResponseDto> =
        rnmApi.fetchAllCharacters().charactersResponse.map { it.mapToCharacterDto() }
}

private fun CharacterResponse.mapToCharacterDto() = CharacterResponseDto(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.parseToLocationShortDto(),
    location = location.parseToLocationShortDto(),
    image = image,
    episodeIds = episode.map { it.obtainId() }
)

private fun LocationShortResponse.parseToLocationShortDto() = LocationShortResponseDto(
    id = url.obtainId(),
    name = name,
)