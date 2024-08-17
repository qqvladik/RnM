package pl.mankevich.network.retrofit

import pl.mankevich.network.retrofit.response.CharacterResponse
import pl.mankevich.network.retrofit.response.CharactersListResponse
import pl.mankevich.network.retrofit.response.EpisodeResponse
import pl.mankevich.network.retrofit.response.EpisodesListResponse
import pl.mankevich.network.retrofit.response.LocationResponse
import pl.mankevich.network.retrofit.response.LocationsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RnmApi {

    @GET("character")
    suspend fun fetchAllCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("status") status: String = "",
        @Query("species") species: String = "",
        @Query("type") type: String = "",
        @Query("gender") gender: String = ""
    ): CharactersListResponse

    @GET("location")
    suspend fun fetchAllLocations(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("type") type: String = "",
        @Query("dimension") dimension: String = ""
    ): LocationsListResponse

    @GET("episode")
    suspend fun fetchAllEpisodes(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("episode") episode: String = ""
    ): EpisodesListResponse

    @GET("character/{ids}")
    suspend fun fetchMultipleCharacters(@Path("ids") ids: List<Int>): List<CharacterResponse>

    @GET("episode/{ids}")
    suspend fun fetchMultipleEpisodes(@Path("ids") ids: List<Int>): List<EpisodeResponse>

    @GET("location/{id}")
    suspend fun fetchSingleLocation(@Path("id") id: Int): LocationResponse

    @GET("character/{id}")
    suspend fun fetchSingleCharacter(@Path("id") id: Int): CharacterResponse

    @GET("episode/{id}")
    suspend fun fetchSingleEpisode(@Path("id") id: Int): EpisodeResponse

}