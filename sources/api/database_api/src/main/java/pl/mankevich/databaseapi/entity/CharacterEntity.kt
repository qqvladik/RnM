package pl.mankevich.databaseapi.entity

data class CharacterEntity(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: LocationShortEntity,
    var location: LocationShortEntity,
    var image: String
)

data class LocationShortEntity(
    var id: Int?,
    var name: String
)

