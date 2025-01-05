package pl.mankevich.databaseroom.room.converter

import androidx.room.TypeConverter
import pl.mankevich.databaseapi.entity.EpisodeFilterEntity

class EpisodeFilterConverter {

    @TypeConverter
    fun fromFilter(filter: EpisodeFilterEntity): String {
        val nameStr = filter.name.buildQueryParam("name")
        val episodeStr = filter.episode.buildQueryParam("episode")

        return nameStr.plus(episodeStr)
    }

    @TypeConverter
    fun toFilter(filterString: String): EpisodeFilterEntity {
        val name = filterString.extractValue("name")
        val episode = filterString.extractValue("episode")

        return EpisodeFilterEntity(
            name = name,
            episode = episode
        )
    }
}