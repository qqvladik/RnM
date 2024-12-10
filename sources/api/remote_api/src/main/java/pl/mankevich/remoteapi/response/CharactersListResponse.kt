package pl.mankevich.remoteapi.response

data class CharactersListResponse(
    val info: InfoResponse,
    val charactersResponse: List<CharacterResponse>
)