package pl.mankevich.network.api.response

data class InfoResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)
