package pl.mankevich.remoteretrofit.retrofit.response

data class LocationRetrofitResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
)