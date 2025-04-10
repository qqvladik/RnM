package pl.mankevich.dataapi.dto

import pl.mankevich.model.Character

data class CharacterDetailResultDto(
    val isOnline: Boolean?, // informs that loading was finished with updating from internet or not
    val character: Character?
)