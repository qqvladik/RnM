package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.model.Episode
import kotlin.collections.plus

typealias EpisodesListTransform = Transform<EpisodesListStateWithEffects>

object EpisodesListTransforms {

    data class ChangeName(
        val name: String
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            val currentFilter = current.state.episodeFilter
            val newFilter = currentFilter.copy(name = name)
            return current.copy(
                state = current.state.copy(
                    episodeFilter = newFilter,
                ),
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.OnLoadEpisodesRequested(newFilter)
                )
            )
        }
    }

    data class ChangeEpisode(
        val episode: String = "",
        val season: String = "" //TODO unite
    ) : EpisodesListTransform {
        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            val currentFilter = current.state.episodeFilter
            val newFilter = currentFilter.copy(episode = episode)
            return current.copy(
                state = current.state.copy(
                    episodeFilter = newFilter,
                    episodeLabelList = current.state.episodeLabelList.addLabelIfUnique(episode),
                    seasonLabelList = current.state.seasonLabelList.addLabelIfUnique(episode),
                ),
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.OnLoadEpisodesRequested(newFilter)
                )
            )
        }
    }

    data class CharacterItemClick(val characterId: Int) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.OnEpisodeItemClicked(characterId)
                )
            )
        }
    }

    data class LoadEpisodesListSuccess(
        val episodes: Flow<PagingData<Episode>>
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    episodes = episodes
                )
            )
        }
    }
}

private fun List<String>.addLabelIfUnique(filter: String): List<String> {
    if (filter.isBlank()) return this
    if (this.any { it.equals(filter, ignoreCase = true) }) return this
    return this.plus(filter)
}
