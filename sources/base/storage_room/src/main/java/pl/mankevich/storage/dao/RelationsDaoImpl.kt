package pl.mankevich.storage.dao

import pl.mankevich.storage.room.dao.RelationsRoomDao
import pl.mankevich.storage.room.entity.EpisodeCharacterEntity
import pl.mankevich.storage.room.entity.LocationCharacterEntity
import pl.mankevich.storageapi.dao.RelationsDao
import javax.inject.Inject

class RelationsDaoImpl
@Inject constructor(
    private val relationsRoomDao: RelationsRoomDao
) : RelationsDao {
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
        relationsRoomDao.insertEpisodeCharacterList(list)
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
        relationsRoomDao.insertLocationCharacterList(list)
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
        relationsRoomDao.insertEpisodeCharacterList(list)
    }
}