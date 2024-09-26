package pl.mankevich.domain.usecase

import kotlinx.coroutines.flow.Flow
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase
import pl.mankevich.model.Episode
import javax.inject.Inject

class LoadEpisodesByCharacterIdUseCaseImpl
@Inject constructor(
    private val episodesRepository: EpisodeRepository,
) : LoadEpisodesByCharacterIdUseCase {

    override fun invoke(characterId: Int): Flow<List<Episode>> =
        episodesRepository.getEpisodesByCharacterId(characterId)
}