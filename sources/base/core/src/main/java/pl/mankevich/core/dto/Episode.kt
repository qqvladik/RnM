package pl.mankevich.core.dto

import java.io.Serializable

data class Episode(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String
) : Serializable
