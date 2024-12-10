package pl.mankevich.databaseroom.dao

import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseroom.room.dao.RelationsRoomDao
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity
import pl.mankevich.databaseroom.room.entity.LocationCharacterRoomEntity
import pl.mankevich.databaseapi.dao.RelationsDao
import javax.inject.Inject

class RelationsDaoImpl
@Inject constructor(
    private val relationsRoomDao: RelationsRoomDao,
) : RelationsDao {

    override fun insertCharacterEpisodes(characterId: Int, episodeIds: List<Int>) { //TODO make it as one query?
        val list: MutableList<EpisodeCharacterRoomEntity> = mutableListOf()
        for (episodeId in episodeIds) {
            list.add(
                EpisodeCharacterRoomEntity(
                    episodeId = episodeId,
                    characterId = characterId
                )
            )
        }
        relationsRoomDao.insertEpisodeCharacterList(list)
    }

    override fun insertLocationCharacters(locationId: Int, characterIds: List<Int>) {
        val list: MutableList<LocationCharacterRoomEntity> = mutableListOf()
        for (characterId in characterIds) {
            list.add(
                LocationCharacterRoomEntity(
                    locationId = locationId,
                    characterId = characterId
                )
            )
        }
        relationsRoomDao.insertLocationCharacterList(list)
    }

    override fun insertEpisodeCharacters(episodeId: Int, characterIds: List<Int>) {
        val list: MutableList<EpisodeCharacterRoomEntity> = mutableListOf()
        for (characterId in characterIds) {
            list.add(
                EpisodeCharacterRoomEntity(
                    episodeId = episodeId,
                    characterId = characterId
                )
            )
        }
        relationsRoomDao.insertEpisodeCharacterList(list)
    }

    override fun getEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>> =
        relationsRoomDao.getEpisodeIdsByCharacterId(characterId)
}
