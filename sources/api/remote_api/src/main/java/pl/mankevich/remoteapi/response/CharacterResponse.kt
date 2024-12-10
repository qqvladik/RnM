package pl.mankevich.remoteapi.response

data class CharacterResponse(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: LocationShortResponse,
    var location: LocationShortResponse,
    var image: String,
    val episodeIds: List<Int>
)

data class LocationShortResponse(
    var id: Int?,
    var name: String
)

