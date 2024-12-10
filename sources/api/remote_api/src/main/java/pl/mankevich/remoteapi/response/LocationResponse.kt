package pl.mankevich.remoteapi.response

data class LocationResponse(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    val residentIds: List<Int>
)
