package pl.mankevich.storageroom.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.storageroom.mapper.mapToCharacterDto
import pl.mankevich.storageroom.mapper.mapToCharacterEntity
import pl.mankevich.storageroom.mapper.mapToCharacterPageKeyDto
import pl.mankevich.storageroom.mapper.mapToCharacterPageKeyEntity
import pl.mankevich.storageroom.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.storageroom.room.dao.CharacterRoomDao
import pl.mankevich.storageapi.dao.CharacterDao
import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.CharacterPageKeyDto
import pl.mankevich.storageapi.dto.FilterDto
import javax.inject.Inject

class CharacterDaoImpl
@Inject constructor(
    private val characterRoomDao: CharacterRoomDao,
    private val characterPageKeyRoomDao: CharacterPageKeyRoomDao
) : CharacterDao() {

    override suspend fun insertCharacter(character: CharacterDto) {
        characterRoomDao.insertCharacter(character.mapToCharacterEntity())
    }

    override suspend fun insertCharactersList(characters: List<CharacterDto>) {
        characterRoomDao.insertCharactersList(
            characters.map { it.mapToCharacterEntity() }
        )
        tableUpdateNotifier.notifyListeners()
    }


    override suspend fun getCharacterById(id: Int): Flow<CharacterDto> =
        characterRoomDao.getCharacterById(id).map { it.mapToCharacterDto() }

    override suspend fun getCharactersByIds(ids: List<Int>): List<CharacterDto> =
        characterRoomDao.getCharactersByIds(ids).map { it.mapToCharacterDto() }

    override suspend fun getCharactersList(
        filterDto: FilterDto,
        limit: Int,
        offset: Int
    ): List<CharacterDto> =
        characterRoomDao.getCharactersList(
            name = filterDto.name,
            status = filterDto.status,
            species = filterDto.species,
            type = filterDto.type,
            gender = filterDto.gender,
            limit = limit,
            offset = offset
        ).map { it.mapToCharacterDto() }

    override suspend fun getCharactersCount(filterDto: FilterDto): Int =
        characterRoomDao.getCount(
            name = filterDto.name,
            status = filterDto.status,
            species = filterDto.species,
            type = filterDto.type,
            gender = filterDto.gender
        )

    override suspend fun insertPageKeysList(pageKeys: List<CharacterPageKeyDto>) {
        characterPageKeyRoomDao.insertPageKeysList(
            pageKeys.map { it.mapToCharacterPageKeyEntity() }
        )
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun getCharacterIds(
        filterDto: FilterDto,
        limit: Int,
        offset: Int
    ): List<Int> =
        characterPageKeyRoomDao.getCharacterIdsByFilter(filterDto, limit, offset)

    override suspend fun getPageKey(characterId: Int, filterDto: FilterDto): CharacterPageKeyDto? =
        characterPageKeyRoomDao.getPageKey(characterId, filterDto)?.mapToCharacterPageKeyDto()

    override suspend fun getPageKeysCount(filterDto: FilterDto): Int =
        characterPageKeyRoomDao.getCount(filterDto)
}
