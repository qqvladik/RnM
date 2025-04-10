package pl.mankevich.domainapi.result

import pl.mankevich.model.Character

data class CharacterDetailResult(
    val isOnline: Boolean?,
    val character: Character?
)