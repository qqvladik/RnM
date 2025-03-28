package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.ui.filter.addLabelIfUnique
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

typealias EpisodesListTransform = Transform<EpisodesListStateWithEffects>

object EpisodesListTransforms {

    data class Init(
        val filter: EpisodeFilter
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            return current.copy(
                state = current.state.copy(
                    episodeFilter = filter,
                    seasonLabelList = current.state.seasonLabelList.addLabelIfUnique(
                        filter.season?.toString() ?: ""
                    ),
                    episodeLabelList = current.state.episodeLabelList.addLabelIfUnique(
                        filter.episode?.toString() ?: ""
                    ),
                ),
            )
        }
    }

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
            )
        }
    }

    data class ChangeSeason(
        val season: Int?,
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            val currentFilter = current.state.episodeFilter
            val newFilter =
                currentFilter.copy(season = season)
            return current.copy(
                state = current.state.copy(
                    episodeFilter = newFilter,
                    seasonLabelList = current.state.seasonLabelList.addLabelIfUnique(
                        season?.toString() ?: ""
                    ),
                ),
            )
        }
    }

    data class ChangeEpisode(
        val episode: Int?,
    ) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            val currentFilter = current.state.episodeFilter
            val newFilter =
                currentFilter.copy(episode = episode)
            return current.copy(
                state = current.state.copy(
                    episodeFilter = newFilter,
                    episodeLabelList = current.state.episodeLabelList.addLabelIfUnique(
                        episode?.toString() ?: ""
                    ),
                ),
            )
        }
    }

    data class EpisodeItemClick(val episodeId: Int) : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.NavigateToEpisodeDetail(episodeId)
                )
            )
        }
    }

    data object BackClick : EpisodesListTransform {

        override fun reduce(current: EpisodesListStateWithEffects): EpisodesListStateWithEffects {
            return current.copy(
                sideEffects = current.sideEffects.add(
                    EpisodesListSideEffect.NavigateBack
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
