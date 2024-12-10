package pl.mankevich.remoteapi.query

data class CharacterFilterQuery(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = ""
)
