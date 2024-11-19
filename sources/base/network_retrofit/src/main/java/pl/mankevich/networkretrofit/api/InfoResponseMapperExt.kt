package pl.mankevich.networkretrofit.api

import pl.mankevich.networkretrofit.retrofit.response.InfoResponse
import pl.mankevich.networkapi.dto.InfoResponseDto

fun InfoResponse.mapToInfoResponseDto() = InfoResponseDto(
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