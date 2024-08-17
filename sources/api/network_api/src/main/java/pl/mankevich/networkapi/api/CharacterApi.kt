package pl.mankevich.networkapi.api

import pl.mankevich.networkapi.dto.CharacterResponseDto
import pl.mankevich.networkapi.dto.CharactersListResponseDto
import pl.mankevich.networkapi.dto.FilterQueryDto

interface CharacterApi {

    suspend fun fetchCharacterById(id: Int): CharacterResponseDto

    suspend fun fetchCharactersByIds(ids: List<Int>): List<CharacterResponseDto>

    suspend fun fetchCharactersList(page: Int, filter: FilterQueryDto): CharactersListResponseDto
}