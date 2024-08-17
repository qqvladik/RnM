package pl.mankevich.networkapi.dto

data class LocationsListResponseDto(
    val info: InfoResponseDto,
    val locationsResponse: List<LocationResponseDto>
)