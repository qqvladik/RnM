package pl.mankevich.core.dto

import java.io.Serializable

data class Location(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String
) : Serializable
