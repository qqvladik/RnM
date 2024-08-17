package pl.mankevich.networkapi.datasource

import pl.mankevich.networkapi.dto.CharacterResponseDto
import pl.mankevich.networkapi.dto.CharactersListResponseDto
import pl.mankevich.networkapi.dto.FilterQueryDto

interface CharacterNetworkDataSource {

    suspend fun fetchCharacterById(id: Int): CharacterResponseDto

    suspend fun fetchCharactersByIds(ids: List<Int>): List<CharacterResponseDto>

    suspend fun fetchCharactersList(page: Int, filter: FilterQueryDto): CharactersListResponseDto
}