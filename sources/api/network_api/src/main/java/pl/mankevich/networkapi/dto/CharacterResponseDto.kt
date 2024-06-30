package pl.mankevich.networkapi.dto

data class CharacterResponseDto(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: LocationShortResponseDto,
    var location: LocationShortResponseDto,
    var image: String,
    val episodeIds: List<Int>
)

data class LocationShortResponseDto(
    var id: Int,
    var name: String
)

