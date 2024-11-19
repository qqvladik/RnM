package pl.mankevich.storageroom.mapper

import pl.mankevich.storageroom.room.entity.CharacterPageKeyEntity
import pl.mankevich.storageapi.dto.CharacterPageKeyDto

fun CharacterPageKeyEntity.mapToCharacterPageKeyDto() = CharacterPageKeyDto(
    characterId = characterId,
    filter = filter,
    value = value,
    previousPageKey = previousPageKey,
    nextPageKey = nextPageKey
)

fun CharacterPageKeyDto.mapToCharacterPageKeyEntity() = CharacterPageKeyEntity(
    characterId = characterId,
    filter = filter,
    value = value,
    previousPageKey = previousPageKey,
    nextPageKey = nextPageKey
)