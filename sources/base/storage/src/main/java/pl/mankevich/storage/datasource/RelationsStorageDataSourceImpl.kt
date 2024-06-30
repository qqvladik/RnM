package pl.mankevich.storage.datasource

import pl.mankevich.storage.db.dao.RelationsDao
import pl.mankevich.storage.db.entity.EpisodeCharacterEntity
import pl.mankevich.storage.db.entity.LocationCharacterEntity
import pl.mankevich.storageapi.datasource.RelationsStorageDataSource
import javax.inject.Inject

class RelationsStorageDataSourceImpl
@Inject constructor(
    private val relationsDao: RelationsDao
) : RelationsStorageDataSource {
    override fun insertCharacterEpisodes(characterId: Int, episodeIds: List<Int>) {
        val list: MutableList<EpisodeCharacterEntity> = mutableListOf()
        for (episodeId in episodeIds) {
            list.add(
                EpisodeCharacterEntity(
                    episodeId = episodeId,
                    characterId = characterId
                )
            )
        }
        relationsDao.insertEpisodeCharacterList(list)
    }

    override fun insertLocationCharacters(locationId: Int, characterIds: List<Int>) {
        val list: MutableList<LocationCharacterEntity> = mutableListOf()
        for (characterId in characterIds) {
            list.add(
                LocationCharacterEntity(
                    locationId = locationId,
                    characterId = characterId
                )
            )
        }
        relationsDao.insertLocationCharacterList(list)
    }

    override fun insertEpisodeCharacters(episodeId: Int, characterIds: List<Int>) {
        val list: MutableList<EpisodeCharacterEntity> = mutableListOf()
        for (characterId in characterIds) {
            list.add(
                EpisodeCharacterEntity(
                    episodeId = episodeId,
                    characterId = characterId
                )
            )
        }
        relationsDao.insertEpisodeCharacterList(list)
    }
}