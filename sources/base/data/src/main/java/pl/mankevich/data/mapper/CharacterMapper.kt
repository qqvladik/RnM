 package pl.mankevich.data.mapper

import pl.mankevich.model.Character
import pl.mankevich.model.LocationShort
import pl.mankevich.remoteapi.response.CharacterResponse
import pl.mankevich.remoteapi.response.LocationShortResponse
import pl.mankevich.databaseapi.entity.CharacterEntity
import pl.mankevich.databaseapi.entity.LocationShortEntity


fun CharacterEntity.mapToCharacter() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.mapToLocationShort(),
    location = location.mapToLocationShort(),
    image = image
)

fun LocationShortEntity.mapToLocationShort() = LocationShort(
    id = id,
    name = name,
)

fun CharacterResponse.mapToCharacterDto() = CharacterEntity(
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

fun LocationShortResponse.mapToLocationShortDto() = LocationShortEntity(
    id = id,
    name = name,
)