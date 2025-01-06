package pl.mankevich.data.mapper

import pl.mankevich.databaseapi.entity.EpisodeFilterEntity
import pl.mankevich.model.EpisodeFilter
import pl.mankevich.remoteapi.query.EpisodeFilterQuery

fun EpisodeFilter.mapToEntity() = EpisodeFilterEntity(
    name = name,
    episode = formatSeasonAndEpisode(season, episode),
)

fun EpisodeFilter.mapToQuery() = EpisodeFilterQuery(
    name = name,
    episode = formatSeasonAndEpisode(season, episode),
)