package pl.mankevich.core.util

// Format the season and episode as "SxxExx"
fun formatSeasonAndEpisode(season: Int?, episode: Int?): String? {
    if (season == null && episode == null) {
        return null
    }
    val seasonStr = if (season != null && season >=0 ) "S%02d".format(season) else ""
    val episodeStr = if (episode != null && episode >= 0) "E%02d".format(episode) else ""
    return seasonStr + episodeStr
}

fun String?.extractSeason(): Int? {
    if (this == null) {
        return null
    }
    val regex = "S(\\d{2})".toRegex()
    val matchResult = regex.find(this)
    return matchResult?.groupValues[1]?.toInt()
}

fun String?.extractEpisode(): Int? {
    if (this == null) {
        return null
    }
    val regex = "E(\\d{2})".toRegex()
    val matchResult = regex.find(this)
    return matchResult?.groupValues[1]?.toInt()
}