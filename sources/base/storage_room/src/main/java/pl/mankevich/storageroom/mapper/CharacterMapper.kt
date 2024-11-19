package pl.mankevich.storageroom.mapper

import pl.mankevich.storageroom.room.entity.CharacterEntity
import pl.mankevich.storageroom.room.entity.LocationEmbedded
import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.LocationShortDto

fun CharacterDto.mapToCharacterEntity() =
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

fun LocationShortDto.mapToLocationEmbedded() = LocationEmbedded(
    id = id,
    name = name,
)

fun CharacterEntity.mapToCharacterDto() = CharacterDto(
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

fun LocationEmbedded.mapToLocationShortDto() = LocationShortDto(
    id = id,
    name = name,
)