package pl.mankevich.episodeslist.presentation

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.core.util.cast
import pl.mankevich.coreui.ui.AppBarWithOffset
import pl.mankevich.coreui.ui.EpisodeCard
import pl.mankevich.coreui.ui.EpisodeCardPlaceholder
import pl.mankevich.coreui.ui.SearchFilterAppBar
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.ui.filter.FilterGroup.Companion.invoke
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
import pl.mankevich.designsystem.component.EmptyView
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.designsystem.utils.rememberGridStateWithClearFocus
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListIntent
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListState
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListViewModel
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

@Composable
fun EpisodesListScreen(
    viewModel: EpisodesListViewModel,
    onEpisodeItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            viewModel.handleSideEffect(sideEffect, onEpisodeItemClick)
        }
    }

    EpisodesListView(
        state = state,
        onSearchChange = { viewModel.sendIntent(EpisodesListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(EpisodesListIntent.NameChanged("")) },
        onSeasonSelected = { viewModel.sendIntent(EpisodesListIntent.SeasonChanged(it.toIntOrNull())) },
        onEpisodeSelected = { viewModel.sendIntent(EpisodesListIntent.EpisodeChanged(it.toIntOrNull())) },
        onEpisodeItemClick = { viewModel.sendIntent(EpisodesListIntent.EpisodeItemClick(it)) },
        onBackPress = onBackPress,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun EpisodesListView(
    state: EpisodesListState,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onSeasonSelected: (String) -> Unit,
    onEpisodeSelected: (String) -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val pagingEpisodeItems = state.episodes.collectAsLazyPagingItems()

    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            val appBarHeight = 108.dp

            AppBarWithOffset(
                modifier = modifier,
                appBarHeight = appBarHeight,
                appBarWithOffset = { appBarOffsetPx ->
                    SearchFilterAppBar(
                        searchValue = state.episodeFilter.name,
                        onSearchChange = onSearchChange,
                        onSearchClear = onSearchClear,
                        filterName = "Locations filter",
                        filterGroupList = listOf(
                            FilterGroup(
                                name = "Season",
                                selected = state.episodeFilter.season.toString(),
                                labelList = state.seasonLabelList,
                                isListFinished = true,
                                resolveIcon = episodeSeasonIconResolver,
                                onSelectedChanged = onSeasonSelected,
                            ),
                            FilterGroup(
                                name = "Episode",
                                selected = state.episodeFilter.episode.toString(),
                                labelList = state.episodeLabelList,
                                isListFinished = false,
                                resolveIcon = episodeEpisodeIconResolver,
                                onSelectedChanged = onEpisodeSelected,
                            ),
                        ),
                        onBackPress = onBackPress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(appBarHeight)
                            .offset { IntOffset(x = 0, y = appBarOffsetPx.toInt()) }
                            .pointerInput(Unit) {} //Helps to prevent clicking on the underlying card elements through spacers
                            .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                            .animateEnterExit(
                                enter = slideInVertically { -it },
                                exit = slideOutVertically { -it }
                            )
                            .background(color = MaterialTheme.colorScheme.background) //must be after renderInSharedTransitionScopeOverlay()
                    )
                },
                content = {
                    val infiniteTransition =
                        rememberInfiniteTransition(label = "LocationsListScreen transition")

                    LazyVerticalStaggeredGrid(
                        state = rememberGridStateWithClearFocus(),
                        columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                        verticalItemSpacing = PADDING,
                        horizontalArrangement = Arrangement.spacedBy(PADDING),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = PADDING)
                    ) {
                        item(span = FullLine) {
                            Spacer(modifier = Modifier.height(appBarHeight - PADDING))
                        }

                        val itemModifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()

                        if (pagingEpisodeItems.loadState.refresh is LoadState.Loading
                            || (pagingEpisodeItems.loadState.refresh is LoadState.NotLoading
                                    && pagingEpisodeItems.itemCount == 0
                                    && !pagingEpisodeItems.loadState.append.endOfPaginationReached)
                        ) {
                            items(20) {
                                EpisodeCardPlaceholder(
                                    infiniteTransition = infiniteTransition,
                                    modifier = itemModifier
                                )
                            }
                        } else if (pagingEpisodeItems.itemCount == 0 && pagingEpisodeItems.loadState.append.endOfPaginationReached) {
                            item(span = FullLine) {
                                EmptyView(modifier = Modifier.fillMaxSize())
                            }
                        } else {
                            episodeListViewData(
                                pagingEpisodeItems = pagingEpisodeItems,
                                onEpisodeItemClick = onEpisodeItemClick,
                                itemModifier = itemModifier
                            )
                        }

                        item(span = FullLine) {}
                    }
                }
            )
        }
    }
}

fun LazyStaggeredGridScope.episodeListViewData(
    pagingEpisodeItems: LazyPagingItems<Episode>,
    onEpisodeItemClick: (Int) -> Unit,
    itemModifier: Modifier
) {
    items(
        count = pagingEpisodeItems.itemCount,
        key = pagingEpisodeItems.itemKey { location -> location.id },
    ) { index ->
        pagingEpisodeItems[index]?.let { episode ->
            EpisodeCard(
                id = episode.id,
                name = episode.name,
                season = episode.season.toString(),
                episode = episode.episode.toString(),
                isFavorite = false,
                onFavoriteClick = {},
                onCardClick = {
                    onEpisodeItemClick(episode.id)
                },
                modifier = itemModifier
            )
        }
    }

    val stateItemModifier = Modifier.fillMaxWidth()

    if (pagingEpisodeItems.loadState.append is LoadState.Loading) {
        item(span = FullLine) {
            LoadingView(
                modifier = stateItemModifier
            )
        }
    }
    if (pagingEpisodeItems.loadState.refresh is LoadState.Error) {
        item(span = FullLine) {
            ErrorView(
                error = pagingEpisodeItems.loadState.refresh.cast<LoadState.Error>().error,
                modifier = stateItemModifier
            ) {
                pagingEpisodeItems.retry()
            }
        }
    }
    if (pagingEpisodeItems.loadState.append is LoadState.Error) {
        item(span = FullLine) {
            ErrorView(
                error = pagingEpisodeItems.loadState.append.cast<LoadState.Error>().error,
                modifier = stateItemModifier
            ) {
                pagingEpisodeItems.retry()
            }
        }
    }
}

@ThemePreviews
@Composable
fun EpisodesListViewPreview() {
    RnmTheme {
        val episode1 = Episode(
            id = 1,
            name = "Pilot",
            airDate = "December 9, 2013",
            season = 1,
            episode = 2,
        )

        EpisodesListView(
            state = EpisodesListState(
                episodeFilter = EpisodeFilter(
                    name = "",
                    episode = null,
                    season = null,
                ),
                episodes = flowOf(
                    value = PagingData.from(
                        data = listOf(
                            episode1,
                            episode1.copy(id = 2)
                        ),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false)
                        ),
                        mediatorLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false)
                        )
                    )
                )
            ),
            onSearchChange = {},
            onSearchClear = {},
            onEpisodeSelected = {},
            onSeasonSelected = {},
            onEpisodeItemClick = {},
            onBackPress = {},
        )
    }
}
