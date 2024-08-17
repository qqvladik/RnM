package pl.mankevich.storageapi.dto

data class CharacterDto(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: LocationShortDto,
    var location: LocationShortDto,
    var image: String
)

data class LocationShortDto(
    var id: Int?,
    var name: String
)

