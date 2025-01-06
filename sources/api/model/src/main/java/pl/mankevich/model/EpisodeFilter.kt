package pl.mankevich.model

data class EpisodeFilter(
    val name: String = "",
    val season: Int? = null,
    val episode: Int? = null,
)
