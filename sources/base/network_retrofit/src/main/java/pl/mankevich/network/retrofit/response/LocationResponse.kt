package pl.mankevich.network.retrofit.response

data class LocationResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
)