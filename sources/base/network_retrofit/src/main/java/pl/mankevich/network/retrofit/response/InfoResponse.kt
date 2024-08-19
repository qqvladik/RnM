package pl.mankevich.network.retrofit.response

data class InfoResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)