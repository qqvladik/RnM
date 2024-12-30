package pl.mankevich.episodeslist.presentation

import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListIntent
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListState
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.ui.filter.FilterGroup.Companion.invoke
import pl.mankevich.coreui.ui.filter.FilterView
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.designsystem.component.EmptyView
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.IconButton
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.SearchField
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

private val PADDING = 12.dp

@Composable
fun EpisodesListScreen(
    viewModel: EpisodesListViewModel,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            viewModel.handleSideEffect(sideEffect, onCharacterItemClick)
        }
    }

    EpisodesListView(
        state = state,
        onSearchChange = { viewModel.sendIntent(EpisodesListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(EpisodesListIntent.NameChanged("")) },
        onEpisodeSelected = { viewModel.sendIntent(EpisodesListIntent.EpisodeChanged(it)) },
        onSeasonSelected = { viewModel.sendIntent(EpisodesListIntent.SeasonChanged(it)) },
        onCharacterItemClick = { viewModel.sendIntent(EpisodesListIntent.CharacterItemClick(it)) },
        onBackPress = onBackPress,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun EpisodesListView(
    state: EpisodesListState,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onEpisodeSelected: (String) -> Unit,
    onSeasonSelected: (String) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val pagingEpisodeItems = state.episodes.collectAsLazyPagingItems()

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(PADDING))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
        ) {
            if (onBackPress != null) {
                IconButton(
                    onClick = onBackPress,
                    imageVector = RnmIcons.CaretLeft,
                    contentDescription = "Show filters",
                    iconSize = 20.dp,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.2f)
                )
            } else {
                Spacer(modifier = Modifier.width(PADDING))
            }

            SearchField(
                value = state.episodeFilter.name,
                onValueChange = onSearchChange,
                onClearClick = onSearchClear,
                placeholder = "Search...",
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(PADDING))
        }

        Spacer(modifier = Modifier.height(PADDING))

        FilterView(
            name = "Episodes filter",
            filterGroupList = listOf(
                FilterGroup(
                    name = "Episode",
                    selected = state.episodeFilter.episode,
                    labelList = state.episodeLabelList,
                    isListFinished = false,
                    resolveIcon = episodeEpisodeIconResolver,
                    onSelectedChanged = onEpisodeSelected,
                ),
                FilterGroup(
                    name = "Season",
                    selected = state.episodeFilter.episode,//TODO: add season
                    labelList = state.seasonLabelList,
                    isListFinished = true,
                    resolveIcon = episodeSeasonIconResolver,
                    onSelectedChanged = onSeasonSelected,
                ),
            ),
            scrollablePadding = PADDING,
            modifier = Modifier
                .height(32.dp)
                .padding(end = PADDING)
        )

        Spacer(modifier = Modifier.height(PADDING))

        /*When use pager with remoteMediator there are strange loadStates when launch app(1->2->3):
            1. loadState.refresh = Loading
             loadState.mediator = null
             loadState.source.refresh = Loading

            2. loadState.refresh = NotLoading
             loadState.mediator.refresh = NotLoading
             loadState.source.refresh = Loading

            3. loadState.refresh = Loading
             loadState.mediator = Loading
             loadState.source.refresh = Loading

            That 2 loadState brings flicking of progressbar. I found the issue: https://issuetracker.google.com/issues/288023763
            There is no such behavior when use pager without remoteMediator.

            That's why pagingEpisodeItems.loadState.append.endOfPaginationReached check was added.
        */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = PADDING)
        ) {
            if (pagingEpisodeItems.loadState.refresh is LoadState.Loading
                || (pagingEpisodeItems.loadState.refresh is LoadState.NotLoading
                        && pagingEpisodeItems.itemCount == 0
                        && !pagingEpisodeItems.loadState.append.endOfPaginationReached)
            ) {
                LoadingView(modifier = Modifier.fillMaxSize())
            } else if (pagingEpisodeItems.itemCount == 0 && pagingEpisodeItems.loadState.append.endOfPaginationReached) {
                EmptyView(modifier = Modifier.fillMaxSize())
            } else {
                val focusManager = LocalFocusManager.current
                val gridState = rememberLazyStaggeredGridState()
                LaunchedEffect(Unit) {//TODO: create custom LazyVerticalGrid with state to clear focus when scroll
                    gridState.interactionSource.interactions
                        .distinctUntilChanged()
                        .filterIsInstance<DragInteraction.Start>()
                        .collectLatest {
                            focusManager.clearFocus()
                        }
                }

                LazyVerticalStaggeredGrid(
                    state = gridState,
                    columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                    verticalItemSpacing = PADDING,
                    horizontalArrangement = Arrangement.spacedBy(PADDING),
                    modifier = Modifier.fillMaxSize()
                ) {
                    val stateItemModifier = Modifier.fillMaxWidth()

                    items(
                        count = pagingEpisodeItems.itemCount,
                        key = pagingEpisodeItems.itemKey { episode -> episode.id },
                    ) { index ->
                        pagingEpisodeItems[index]?.let { episode ->
                            Text(text = episode.toString())
                        }
                    }

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

                    item(span = FullLine) {}
                }
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
            episode = "S01E01",
        )

        EpisodesListView(
            state = EpisodesListState(
                episodeFilter = EpisodeFilter(
                    name = "",
                    episode = "",
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
            onCharacterItemClick = {},
            onBackPress = {},
        )
    }
}
