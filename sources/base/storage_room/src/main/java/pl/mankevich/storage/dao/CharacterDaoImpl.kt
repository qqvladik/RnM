package pl.mankevich.storage.dao

import androidx.room.withTransaction
import pl.mankevich.storage.room.RnmDatabase
import pl.mankevich.storage.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.storage.room.dao.CharacterRoomDao
import pl.mankevich.storage.room.entity.CharacterEntity
import pl.mankevich.storage.room.entity.CharacterPageKeyEntity
import pl.mankevich.storage.room.entity.LocationEmbedded
import pl.mankevich.storageapi.dao.CharacterDao
import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.CharacterPageKeyDto
import pl.mankevich.storageapi.dto.FilterDto
import pl.mankevich.storageapi.dto.LocationShortDto
import javax.inject.Inject

class CharacterDaoImpl
@Inject constructor(
    private val rnmDatabase: RnmDatabase,
    private val characterRoomDao: CharacterRoomDao,
    private val characterPageKeyRoomDao: CharacterPageKeyRoomDao
) : CharacterDao() {

    override suspend fun insertCharactersAndPageKeys( //TODO remove in future
        characters: List<CharacterDto>,
        pageKeys: List<CharacterPageKeyDto>
    ) {
        rnmDatabase.withTransaction {
            insertCharactersList(characters)
            insertPageKeysList(pageKeys)
        }
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun insertCharactersList(characters: List<CharacterDto>) {
        characterRoomDao.insertCharactersList(
            characters.map { it.mapToCharacterEntity() }
        )
        tableUpdateNotifier.notifyListeners()
    }


    override suspend fun getCharacterById(id: Int): CharacterDto =
        characterRoomDao.getCharacterById(id).mapToCharacterDto()

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

    override suspend fun getCharacterIdsByFilter(
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

private fun CharacterDto.mapToCharacterEntity() =
    CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.mapToLocationEmbedded(),
        location = location.mapToLocationEmbedded(),
        image = image
    )

private fun LocationShortDto.mapToLocationEmbedded() = LocationEmbedded(
    id = id,
    name = name,
)

private fun CharacterEntity.mapToCharacterDto() = CharacterDto(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.mapToLocationShortDto(),
    location = location.mapToLocationShortDto(),
    image = image
)

private fun LocationEmbedded.mapToLocationShortDto() = LocationShortDto(
    id = id,
    name = name,
)

private fun CharacterPageKeyEntity.mapToCharacterPageKeyDto() = CharacterPageKeyDto(
    characterId = characterId,
    filter = filter,
    value = value,
    previousPageKey = previousPageKey,
    nextPageKey = nextPageKey
)

private fun CharacterPageKeyDto.mapToCharacterPageKeyEntity() = CharacterPageKeyEntity(
    characterId = characterId,
    filter = filter,
    value = value,
    previousPageKey = previousPageKey,
    nextPageKey = nextPageKey
)
