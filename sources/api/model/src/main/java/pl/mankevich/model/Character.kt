package pl.mankevich.model

data class Character(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: LocationShort,
    var location: LocationShort,
    var image: String
)

data class LocationShort(
    var id: Int?,
    var name: String
)

