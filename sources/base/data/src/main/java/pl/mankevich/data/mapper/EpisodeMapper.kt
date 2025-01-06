package pl.mankevich.data.mapper

import pl.mankevich.model.Episode
import pl.mankevich.remoteapi.response.EpisodeResponse
import pl.mankevich.databaseapi.entity.EpisodeEntity

fun EpisodeEntity.mapToEpisode() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    season = extractSeason(episode)!!,
    episode = extractEpisode(episode)!!,
)

fun EpisodeResponse.mapToEpisodeDto() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)