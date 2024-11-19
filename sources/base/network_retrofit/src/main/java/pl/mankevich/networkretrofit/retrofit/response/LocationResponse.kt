package pl.mankevich.networkretrofit.retrofit.response

data class LocationResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
)