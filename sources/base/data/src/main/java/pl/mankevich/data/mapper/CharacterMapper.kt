 package pl.mankevich.data.mapper

import pl.mankevich.model.Character
import pl.mankevich.model.LocationShort
import pl.mankevich.networkapi.dto.CharacterResponseDto
import pl.mankevich.networkapi.dto.LocationShortResponseDto
import pl.mankevich.storageapi.dto.CharacterDto
import pl.mankevich.storageapi.dto.LocationShortDto


fun CharacterDto.mapToCharacter() = Character(
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

fun LocationShortDto.mapToLocationShort() = LocationShort(
    id = id,
    name = name,
)

fun CharacterResponseDto.mapToCharacterDto() = CharacterDto(
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

fun LocationShortResponseDto.mapToLocationShortDto() = LocationShortDto(
    id = id,
    name = name,
)