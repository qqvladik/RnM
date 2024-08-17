package pl.mankevich.networkapi.dto

data class CharactersListResponseDto(
    val info: InfoResponseDto,
    val charactersResponse: List<CharacterResponseDto>
)