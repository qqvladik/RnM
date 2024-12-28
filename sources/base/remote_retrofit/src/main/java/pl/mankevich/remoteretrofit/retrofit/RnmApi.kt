package pl.mankevich.remoteretrofit.retrofit

import pl.mankevich.core.CHARACTER_RELATIVE_PATH
import pl.mankevich.core.EPISODE_RELATIVE_PATH
import pl.mankevich.core.LOCATION_RELATIVE_PATH
import pl.mankevich.remoteretrofit.retrofit.response.CharacterRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.CharactersListRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.EpisodeRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.EpisodesListRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.LocationRetrofitResponse
import pl.mankevich.remoteretrofit.retrofit.response.LocationsListRetrofitResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RnmApi {

    @GET(CHARACTER_RELATIVE_PATH)
    suspend fun fetchAllCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("status") status: String = "",
        @Query("species") species: String = "",
        @Query("type") type: String = "",
        @Query("gender") gender: String = ""
    ): CharactersListRetrofitResponse

    @GET(LOCATION_RELATIVE_PATH)
    suspend fun fetchAllLocations(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("type") type: String = "",
        @Query("dimension") dimension: String = ""
    ): LocationsListRetrofitResponse

    @GET(EPISODE_RELATIVE_PATH)
    suspend fun fetchAllEpisodes(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("episode") episode: String = ""
    ): EpisodesListRetrofitResponse

    @GET("$CHARACTER_RELATIVE_PATH/{ids}")
    suspend fun fetchMultipleCharacters(@Path("ids") ids: List<Int>): List<CharacterRetrofitResponse>

    @GET("$EPISODE_RELATIVE_PATH/{ids}")
    suspend fun fetchMultipleEpisodes(@Path("ids") ids: List<Int>): List<EpisodeRetrofitResponse>

    @GET("$LOCATION_RELATIVE_PATH/{id}")
    suspend fun fetchSingleLocation(@Path("id") id: Int): LocationRetrofitResponse

    @GET("$CHARACTER_RELATIVE_PATH/{id}")
    suspend fun fetchSingleCharacter(@Path("id") id: Int): CharacterRetrofitResponse

    @GET("$EPISODE_RELATIVE_PATH/{id}")
    suspend fun fetchSingleEpisode(@Path("id") id: Int): EpisodeRetrofitResponse

}