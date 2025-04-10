package pl.mankevich.domainapi.result

import pl.mankevich.model.Episode

data class EpisodeDetailResult(
    val isOnline: Boolean?,
    val episode: Episode?
)