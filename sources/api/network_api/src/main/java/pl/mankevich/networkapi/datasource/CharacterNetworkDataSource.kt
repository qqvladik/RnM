package pl.mankevich.networkapi.datasource

import pl.mankevich.networkapi.dto.CharacterResponseDto

interface CharacterNetworkDataSource {

    suspend fun getCharacterById(id: Int): CharacterResponseDto

    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterResponseDto>

    suspend fun getCharactersList(): List<CharacterResponseDto>
}