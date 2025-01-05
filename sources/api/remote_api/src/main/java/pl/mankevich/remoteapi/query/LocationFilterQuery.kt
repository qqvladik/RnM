package pl.mankevich.remoteapi.query

data class LocationFilterQuery(
    val name: String = "",
    val type: String = "",
    val dimension: String = ""
)
