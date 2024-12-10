package pl.mankevich.remoteretrofit.api

import pl.mankevich.remoteretrofit.retrofit.response.InfoRetrofitResponse
import pl.mankevich.remoteapi.response.InfoResponse

fun InfoRetrofitResponse.mapToInfoResponseDto() = InfoResponse(
    count = count,
    pages = pages,
    next = next.obtainPage(),
    prev = prev.obtainPage()
)

fun String?.obtainPage(): Int? {
    if (this.isNullOrBlank()) return null
    return this
        .substringAfterLast("page=")
        .substringBefore("&")
        .toInt()
}