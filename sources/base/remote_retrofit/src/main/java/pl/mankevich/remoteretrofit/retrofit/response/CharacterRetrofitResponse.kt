package pl.mankevich.remoteretrofit.retrofit.response

data class CharacterRetrofitResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationShortRetrofitResponse,
    val location: LocationShortRetrofitResponse,
    val image: String,
    val episode: List<String>
)

data class LocationShortRetrofitResponse(
    val name: String,
    val url: String
)
