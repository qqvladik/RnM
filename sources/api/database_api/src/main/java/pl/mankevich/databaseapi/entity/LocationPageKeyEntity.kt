package pl.mankevich.databaseapi.entity

data class LocationPageKeyEntity(
    val locationId: Int,
    val filter: LocationFilterEntity,
    val value: Int,
    val previousPageKey: Int? = null,
    val nextPageKey: Int? = null
)