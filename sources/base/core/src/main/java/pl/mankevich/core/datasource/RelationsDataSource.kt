package pl.mankevich.core.datasource

interface RelationsDataSource {

    fun insertCharacterEpisodes(characterId: Int, episodeIds: List<Int>)

    fun insertLocationCharacters(locationId: Int, characterIds: List<Int>)

    fun insertEpisodeCharacters(episodeId: Int, characterIds: List<Int>)
}