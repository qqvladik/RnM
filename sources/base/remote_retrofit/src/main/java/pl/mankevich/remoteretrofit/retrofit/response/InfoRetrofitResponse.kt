package pl.mankevich.remoteretrofit.retrofit.response

data class InfoRetrofitResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)
