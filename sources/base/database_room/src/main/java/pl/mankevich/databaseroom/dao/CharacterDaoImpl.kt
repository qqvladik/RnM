package pl.mankevich.databaseroom.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.databaseapi.dao.CharacterDao
import pl.mankevich.databaseapi.entity.CharacterEntity
import pl.mankevich.databaseapi.entity.CharacterPageKeyEntity
import pl.mankevich.databaseapi.entity.CharacterFilterEntity
import pl.mankevich.databaseroom.mapper.mapToEntity
import pl.mankevich.databaseroom.mapper.mapToRoom
import pl.mankevich.databaseroom.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.CharacterRoomDao
import javax.inject.Inject

class CharacterDaoImpl
@Inject constructor(
    private val characterRoomDao: CharacterRoomDao,
    private val characterPageKeyRoomDao: CharacterPageKeyRoomDao
) : CharacterDao() {

    override suspend fun insertCharacter(character: CharacterEntity) {
        characterRoomDao.insertCharacter(character.mapToRoom())
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun insertCharactersList(characters: List<CharacterEntity>) {
        characterRoomDao.insertCharactersList(
            characters.map { it.mapToRoom() }
        )
        tableUpdateNotifier.notifyListeners()
    }

    override fun getCharacterById(id: Int): Flow<CharacterEntity> =
        characterRoomDao.getCharacterById(id).map { it.mapToEntity() }

    override suspend fun getCharactersByIds(ids: List<Int>): List<CharacterEntity> =
        characterRoomDao.getCharactersByIds(ids).map { it.mapToEntity() }

    override fun getCharactersFlowByIds(ids: List<Int>): Flow<List<CharacterEntity>> =
        characterRoomDao.getCharactersFlowByIds(ids).map { list ->
            list.map { it.mapToEntity() }
        }

    override suspend fun getCharactersList(
        characterFilterEntity: CharacterFilterEntity,
        limit: Int,
        offset: Int
    ): List<CharacterEntity> =
        characterRoomDao.getCharactersList(
            name = characterFilterEntity.name,
            status = characterFilterEntity.status,
            species = characterFilterEntity.species,
            type = characterFilterEntity.type,
            gender = characterFilterEntity.gender,
            limit = limit,
            offset = offset
        ).map { it.mapToEntity() }

    override suspend fun getCharactersCount(characterFilterEntity: CharacterFilterEntity): Int =
        characterRoomDao.getCount(
            name = characterFilterEntity.name,
            status = characterFilterEntity.status,
            species = characterFilterEntity.species,
            type = characterFilterEntity.type,
            gender = characterFilterEntity.gender
        )

    override suspend fun getCharacterIds(
        characterFilterEntity: CharacterFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int> =
        characterPageKeyRoomDao.getCharacterIdsByFilter(characterFilterEntity, limit, offset)

    override suspend fun insertPageKeysList(pageKeys: List<CharacterPageKeyEntity>) {
        characterPageKeyRoomDao.insertPageKeysList(
            pageKeys.map { it.mapToRoom() }
        )
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun getPageKey(
        characterId: Int,
        characterFilterEntity: CharacterFilterEntity
    ): CharacterPageKeyEntity? =
        characterPageKeyRoomDao.getPageKey(characterId, characterFilterEntity)?.mapToEntity()

    override suspend fun getPageKeysCount(characterFilterEntity: CharacterFilterEntity): Int =
        characterPageKeyRoomDao.getCount(characterFilterEntity)
}
