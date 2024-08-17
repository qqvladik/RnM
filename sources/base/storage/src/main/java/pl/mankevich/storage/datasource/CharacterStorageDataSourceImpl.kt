package pl.mankevich.storage.datasource

import androidx.room.withTransaction
import pl.mankevich.storage.db.RnmDatabase
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.dao.CharacterPageKeyDao
import pl.mankevich.storage.db.entity.CharacterEntity
import pl.mankevich.storage.db.entity.CharacterPageKeyEntity
import pl.mankevich.storage.db.entity.LocationEmbedded
import pl.mankevich.storageapi.datasource.CharacterStorageDataSource
import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.CharacterPageKeyDto
import pl.mankevich.storageapi.dto.FilterDto
import pl.mankevich.storageapi.dto.LocationShortDto
import javax.inject.Inject

class CharacterStorageDataSourceImpl
@Inject constructor(
    private val rnmDatabase: RnmDatabase,
    private val characterDao: CharacterDao,
    private val characterPageKeyDao: CharacterPageKeyDao
) : CharacterStorageDataSource() {

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
        characterDao.insertCharactersList(
            characters.map { it.mapToCharacterEntity() }
        )
        tableUpdateNotifier.notifyListeners()
    }


    override suspend fun getCharacterById(id: Int): CharacterDto =
        characterDao.getCharacterById(id).mapToCharacterDto()

    override suspend fun getCharactersByIds(ids: List<Int>): List<CharacterDto> =
        characterDao.getCharactersByIds(ids).map { it.mapToCharacterDto() }

    override suspend fun getCharactersList(
        filterDto: FilterDto,
        limit: Int,
        offset: Int
    ): List<CharacterDto> =
        characterDao.getCharactersList(
            name = filterDto.name,
            status = filterDto.status,
            species = filterDto.species,
            type = filterDto.type,
            gender = filterDto.gender,
            limit = limit,
            offset = offset
        ).map { it.mapToCharacterDto() }

    override suspend fun getCharactersCount(filterDto: FilterDto): Int =
        characterDao.getCount(
            name = filterDto.name,
            status = filterDto.status,
            species = filterDto.species,
            type = filterDto.type,
            gender = filterDto.gender
        )

    override suspend fun insertPageKeysList(pageKeys: List<CharacterPageKeyDto>) {
        characterPageKeyDao.insertPageKeysList(
            pageKeys.map { it.mapToCharacterPageKeyEntity() }
        )
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun getCharacterIdsByFilter(
        filterDto: FilterDto,
        limit: Int,
        offset: Int
    ): List<Int> =
        characterPageKeyDao.getCharacterIdsByFilter(filterDto, limit, offset)

    override suspend fun getPageKey(characterId: Int, filterDto: FilterDto): CharacterPageKeyDto? =
        characterPageKeyDao.getPageKey(characterId, filterDto)?.mapToCharacterPageKeyDto()

    override suspend fun getPageKeysCount(filterDto: FilterDto): Int =
        characterPageKeyDao.getCount(filterDto)
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
