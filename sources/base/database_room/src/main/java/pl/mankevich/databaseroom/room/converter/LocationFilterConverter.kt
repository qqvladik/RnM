package pl.mankevich.databaseroom.room.converter

import androidx.room.TypeConverter
import pl.mankevich.databaseapi.entity.LocationFilterEntity

class LocationFilterConverter {

    @TypeConverter
    fun fromFilter(filter: LocationFilterEntity): String {
        val nameStr = filter.name.buildQueryParam("name")
        val typeStr = filter.type.buildQueryParam("type")
        val dimensionStr = filter.dimension.buildQueryParam("dimension")

        return nameStr.plus(typeStr).plus(dimensionStr)
    }

    @TypeConverter
    fun toFilter(filterString: String): LocationFilterEntity {
        val name = filterString.extractValue("name")
        val type = filterString.extractValue("type")
        val dimension = filterString.extractValue("dimension")

        return LocationFilterEntity(
            name = name,
            type = type,
            dimension = dimension
        )
    }
}