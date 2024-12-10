package pl.mankevich.remoteapi.response

data class LocationsListResponse(
    val info: InfoResponse,
    val locationsResponse: List<LocationResponse>
)