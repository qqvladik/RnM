package pl.mankevich.storageapi.dto

data class CharacterPageKeyDto(
    val characterId: Int,
    val filter: FilterDto,
    val value: Int,
    val previousPageKey: Int? = null,
    val nextPageKey: Int? = null
)