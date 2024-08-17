package pl.mankevich.storageapi.dto

data class FilterDto(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = ""
)
