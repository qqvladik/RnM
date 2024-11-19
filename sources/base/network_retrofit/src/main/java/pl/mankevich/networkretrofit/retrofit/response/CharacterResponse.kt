package pl.mankevich.networkretrofit.retrofit.response

data class CharacterResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationShortResponse,
    val location: LocationShortResponse,
    val image: String,
    val episode: List<String>
)

data class LocationShortResponse(
    val name: String,
    val url: String
)
