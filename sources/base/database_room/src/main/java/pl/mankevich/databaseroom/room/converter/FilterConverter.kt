package pl.mankevich.databaseroom.room.converter

import androidx.room.TypeConverter
import pl.mankevich.databaseapi.entity.CharacterFilterEntity

class FilterConverter {

    @TypeConverter
    fun fromFilter(filter: CharacterFilterEntity): String {
        val nameStr = filter.name.buildQueryParam("name")
        val statusStr = filter.status.buildQueryParam("status")
        val speciesStr = filter.species.buildQueryParam("species")
        val typeStr = filter.type.buildQueryParam("type")
        val genderStr = filter.gender.buildQueryParam("gender")

        return nameStr.plus(statusStr).plus(speciesStr).plus(typeStr).plus(genderStr)
    }

    private fun String.buildQueryParam(key: String): String {
        return this.ifBlank { null }?.let { "&$key=$it" }.orEmpty()
    }

    @TypeConverter
    fun toFilter(filterString: String): CharacterFilterEntity {
        val name = filterString.extractValue("name")
        val status = filterString.extractValue("status")
        val species = filterString.extractValue("species")
        val type = filterString.extractValue("type")
        val gender = filterString.extractValue("gender")

        return CharacterFilterEntity(name, status, species, type, gender)
    }

    private fun String.extractValue(key: String): String {
        return this
            .substringAfter("&$key=", "")
            .substringBefore("&")
    }
}