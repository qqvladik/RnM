package pl.mankevich.remoteapi.response

data class InfoResponse(
    val count: Int,
    val pages: Int,
    val next: Int?,
    val prev: Int?,
)
