package pl.mankevich.databaseapi.entity

data class EpisodePageKeyEntity(
    val episodeId: Int,
    val filter: EpisodeFilterEntity,
    val value: Int,
    val previousPageKey: Int? = null,
    val nextPageKey: Int? = null
)