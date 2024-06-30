package pl.mankevich.networkapi.dto

data class EpisodeResponseDto(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String,
    val characterIds: List<Int>
)
