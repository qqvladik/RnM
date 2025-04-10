package pl.mankevich.dataapi.dto

import pl.mankevich.model.Episode

data class EpisodeDetailResultDto(
    val isOnline: Boolean?,
    val episode: Episode?
)