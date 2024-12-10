package pl.mankevich.remoteapi.api

import pl.mankevich.remoteapi.response.CharacterResponse
import pl.mankevich.remoteapi.response.CharactersListResponse
import pl.mankevich.remoteapi.query.CharacterFilterQuery

interface CharacterApi {

    suspend fun fetchCharacterById(id: Int): CharacterResponse

    suspend fun fetchCharactersByIds(ids: List<Int>): List<CharacterResponse>

    suspend fun fetchCharactersList(page: Int, filter: CharacterFilterQuery): CharactersListResponse
}