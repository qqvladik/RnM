package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.ui.filter.addLabelIfUnique
import pl.mankevich.model.Episode

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

    data class ChangeSeason(
        val season: String = "",
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            val currentFilter = current.state.episodeFilter
            val newFilter =
                currentFilter.copy(season = season.toIntOrNull())
            return current.copy(
                state = current.state.copy(
                    episodeFilter = newFilter,
                    seasonLabelList = current.state.seasonLabelList.addLabelIfUnique(season),
                ),
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.OnLoadEpisodesRequested(newFilter)
                )
            )
        }
    }

    data class ChangeEpisode(
        val episode: String = "",
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            val currentFilter = current.state.episodeFilter
            val newFilter =
                currentFilter.copy(episode = episode.toIntOrNull())
            return current.copy(
                state = current.state.copy(
                    episodeFilter = newFilter,
                    episodeLabelList = current.state.episodeLabelList.addLabelIfUnique(episode),
                ),
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.OnLoadEpisodesRequested(newFilter)
                )
            )
        }
    }

    data class EpisodeItemClick(val characterId: Int) : EpisodesListTransform {

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
