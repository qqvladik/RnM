package pl.mankevich.storage.db.datasource

import pl.mankevich.core.datasource.CharacterDataSource
import pl.mankevich.core.dto.Character
import pl.mankevich.core.dto.LocationShort
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.entity.CharacterEntity
import pl.mankevich.storage.db.entity.LocationEmbedded
import javax.inject.Inject

class CharacterDataSourceImpl
@Inject constructor(
    private val characterDao: CharacterDao
) : CharacterDataSource {

    override suspend fun insertCharactersList(characters: List<Character>) =
        characterDao.insertCharactersList(
            characters.map { it.mapToCharacterEntity() }
        )

    override suspend fun getCharacterById(id: Int): Character? =
        characterDao.getCharacterById(id)?.mapToCharacter()

    override suspend fun getCharactersByIds(ids: List<Int>): List<Character> =
        characterDao.getCharactersByIds(ids).map { it.mapToCharacter() }

    override suspend fun getCharactersList(): List<Character> =
        characterDao.getCharactersList().map { it.mapToCharacter() }
}

private fun Character.mapToCharacterEntity() = CharacterEntity(
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

private fun LocationShort.parseToLocationEmbedded() = LocationEmbedded(
    id = id,
    name = name,
)

private fun CharacterEntity.mapToCharacter() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.parseToLocationShort(),
    location = location.parseToLocationShort(),
    image = image
)

private fun LocationEmbedded.parseToLocationShort() = LocationShort(
    id = id,
    name = name,
)
