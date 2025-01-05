package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.domainapi.usecase.LoadCharactersByEpisodeIdUseCase
import pl.mankevich.model.Character
import javax.inject.Inject

class LoadCharactersByEpisodeIdUseCaseImpl
@Inject constructor(
    private val characterRepository: CharacterRepository,
) : LoadCharactersByEpisodeIdUseCase {

    override fun invoke(episodeId: Int): Flow<List<Character>> =
        characterRepository.getCharactersByEpisodeId(episodeId)
}