package pl.mankevich.remoteapi.response

data class EpisodeResponse(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String,
    val characterIds: List<Int>
)
