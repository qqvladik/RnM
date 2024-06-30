package pl.mankevich.networkapi.dto

data class LocationResponseDto(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    val residentIds: List<Int>
)
