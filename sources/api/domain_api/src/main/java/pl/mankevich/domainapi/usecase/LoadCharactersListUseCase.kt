package pl.mankevich.domainapi.usecase

import pl.mankevich.domainapi.result.CharactersResult
import pl.mankevich.model.CharacterFilter

interface LoadCharactersListUseCase {

    suspend operator fun invoke(characterFilter: CharacterFilter): CharactersResult
}