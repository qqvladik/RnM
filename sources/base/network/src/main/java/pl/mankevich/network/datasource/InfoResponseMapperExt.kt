package pl.mankevich.network.datasource

import pl.mankevich.network.api.response.InfoResponse
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