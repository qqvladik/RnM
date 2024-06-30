package pl.mankevich.storage.datasource

import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.LocationShortDto
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.entity.CharacterEntity
import pl.mankevich.storage.db.entity.LocationEmbedded
import pl.mankevich.storageapi.datasource.CharacterStorageDataSource
import javax.inject.Inject

class CharacterStorageDataSourceImpl
@Inject constructor(
    private val characterDao: CharacterDao
) : CharacterStorageDataSource {

    override suspend fun insertCharactersList(characters: List<CharacterDto>) =
        characterDao.insertCharactersList(
            characters.map { it.mapToCharacterEntity() }
        )

    override suspend fun getCharacterById(id: Int): CharacterDto =
        characterDao.getCharacterById(id).mapToCharacterDto()

    override suspend fun getCharactersByIds(ids: List<Int>): List<CharacterDto> =
        characterDao.getCharactersByIds(ids).map { it.mapToCharacterDto() }

    override suspend fun getCharactersList(): List<CharacterDto> =
        characterDao.getCharactersList().map { it.mapToCharacterDto() }
}

private fun CharacterDto.mapToCharacterEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.parseToLocationEmbedded(),
    location = location.parseToLocationEmbedded(),
    image = image
)

private fun LocationShortDto.parseToLocationEmbedded() = LocationEmbedded(
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
    origin = origin.parseToLocationShortDto(),
    location = location.parseToLocationShortDto(),
    image = image
)

private fun LocationEmbedded.parseToLocationShortDto() = LocationShortDto(
    id = id,
    name = name,
)
