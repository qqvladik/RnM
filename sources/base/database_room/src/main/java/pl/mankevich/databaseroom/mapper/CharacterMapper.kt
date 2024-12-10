package pl.mankevich.databaseroom.mapper

import pl.mankevich.databaseapi.entity.CharacterEntity
import pl.mankevich.databaseroom.room.entity.LocationEmbedded
import pl.mankevich.databaseapi.entity.LocationShortEntity
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity

fun CharacterEntity.mapToRoom() =
    CharacterRoomEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.mapToRoom(),
        location = location.mapToRoom(),
        image = image
    )

fun LocationShortEntity.mapToRoom() = LocationEmbedded(
    id = id,
    name = name,
)

fun CharacterRoomEntity.mapToEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.mapToEntity(),
    location = location.mapToEntity(),
    image = image
)

fun LocationEmbedded.mapToEntity() = LocationShortEntity(
    id = id,
    name = name,
)