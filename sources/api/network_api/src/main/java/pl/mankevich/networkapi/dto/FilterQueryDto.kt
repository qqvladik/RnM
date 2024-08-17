package pl.mankevich.networkapi.dto

data class FilterQueryDto(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = ""
)
