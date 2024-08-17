package pl.mankevich.networkapi.dto

data class InfoResponseDto(
    val count: Int,
    val pages: Int,
    val next: Int?,
    val prev: Int?,
)
