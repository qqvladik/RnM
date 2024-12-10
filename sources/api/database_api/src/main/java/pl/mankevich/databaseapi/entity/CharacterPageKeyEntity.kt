package pl.mankevich.databaseapi.entity

data class CharacterPageKeyEntity(
    val characterId: Int,
    val filter: CharacterFilterEntity,
    val value: Int,
    val previousPageKey: Int? = null,
    val nextPageKey: Int? = null
)