package pl.mankevich.network.api

import pl.mankevich.network.retrofit.response.InfoResponse
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