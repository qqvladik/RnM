package pl.mankevich.databaseroom.mapper

import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity
import pl.mankevich.databaseapi.entity.CharacterPageKeyEntity

fun CharacterPageKeyRoomEntity.mapToEntity() = CharacterPageKeyEntity(
    characterId = characterId,
    filter = filter,
    value = value,
    previousPageKey = previous,
    nextPageKey = next
)

fun CharacterPageKeyEntity.mapToRoom() =
    CharacterPageKeyRoomEntity(
        characterId = characterId,
        filter = filter,
        value = value,
        previous = previousPageKey,
        next = nextPageKey
    )