package pl.mankevich.data.mapper

import pl.mankevich.core.util.extractEpisode
import pl.mankevich.core.util.extractSeason
import pl.mankevich.model.Episode
import pl.mankevich.remoteapi.response.EpisodeResponse
import pl.mankevich.databaseapi.entity.EpisodeEntity

fun EpisodeEntity.mapToEpisode() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    season = episode.extractSeason()!!,
    episode = episode.extractEpisode()!!,
)

fun EpisodeResponse.mapToEpisodeDto() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)