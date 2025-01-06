package pl.mankevich.data.mapper

// Format the season and episode as "SxxExx"
fun formatSeasonAndEpisode(season: Int?, episode: Int?): String {
    val seasonStr = if (season != null) "S%02d".format(season) else ""
    val episodeStr = if (episode != null) "E%02d".format(episode) else ""
    return seasonStr + episodeStr
}

fun extractSeason(input: String): Int? {
    val regex = "S(\\d{2})".toRegex()
    val matchResult = regex.find(input)
    return matchResult?.groupValues[1]?.toInt()
}

fun extractEpisode(input: String): Int? {
    val regex = "E(\\d{2})".toRegex()
    val matchResult = regex.find(input)
    return matchResult?.groupValues[1]?.toInt()
}