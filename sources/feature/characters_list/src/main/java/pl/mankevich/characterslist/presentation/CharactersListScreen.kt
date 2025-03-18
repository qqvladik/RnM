package pl.mankevich.characterslist.presentation

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListIntent
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListState
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.coreui.ui.CharacterCard
import pl.mankevich.coreui.ui.CharacterCardPlaceholder
import pl.mankevich.coreui.ui.SearchFilterAppBar
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.utils.characterGenderIconResolver
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.coreui.utils.characterTypeIconResolver
import pl.mankevich.designsystem.component.EmptyView
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.FlexibleTopBar
import pl.mankevich.designsystem.component.FlexibleTopBarDefaults
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.designsystem.utils.rememberGridStateWithClearFocus
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import pl.mankevich.model.LocationShort

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    navigateToCharacterDetail: (Int) -> Unit,
    navigateUp: (() -> Unit)? = null,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            viewModel.handleSideEffect(
                sideEffect = sideEffect,
                navigateToCharacterDetail = navigateToCharacterDetail,
                navigateBack = navigateUp,
            )
        }
    }

    CharactersListView(
        state = state,
        onSearchChange = { viewModel.sendIntent(CharactersListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(CharactersListIntent.NameChanged("")) },
        onStatusSelected = { viewModel.sendIntent(CharactersListIntent.StatusChanged(it)) },
        onSpeciesSelected = { viewModel.sendIntent(CharactersListIntent.SpeciesChanged(it)) },
        onGenderSelected = { viewModel.sendIntent(CharactersListIntent.GenderChanged(it)) },
        onTypeSelected = { viewModel.sendIntent(CharactersListIntent.TypeChanged(it)) },
        onCharacterItemClick = { viewModel.sendIntent(CharactersListIntent.CharacterItemClick(it)) },
        onBackPress = navigateUp,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun CharactersListView(
    state: CharactersListState,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onStatusSelected: (String) -> Unit,
    onSpeciesSelected: (String) -> Unit,
    onGenderSelected: (String) -> Unit,
    onTypeSelected: (String) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val pagingCharacterItems = state.characters.collectAsLazyPagingItems()

    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            Scaffold(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    FlexibleTopBar(
                        scrollBehavior = scrollBehavior,
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SearchFilterAppBar(
                            searchValue = state.characterFilter.name,
                            onSearchChange = onSearchChange,
                            onSearchClear = onSearchClear,
                            filterName = "Characters filter",
                            filterGroupList = listOf(
                                FilterGroup(
                                    name = "Status",
                                    selected = state.characterFilter.status,
                                    labelList = state.statusLabelList,
                                    isListFinished = true,
                                    resolveIcon = characterStatusIconResolver,
                                    onSelectedChanged = onStatusSelected,
                                ),
                                FilterGroup(
                                    name = "Species",
                                    selected = state.characterFilter.species,
                                    labelList = state.speciesLabelList,
                                    isListFinished = false,
                                    resolveIcon = characterSpeciesIconResolver,
                                    onSelectedChanged = onSpeciesSelected,
                                ),
                                FilterGroup(
                                    name = "Gender",
                                    selected = state.characterFilter.gender,
                                    labelList = state.genderLabelList,
                                    isListFinished = true,
                                    resolveIcon = characterGenderIconResolver,
                                    onSelectedChanged = onGenderSelected,
                                ),
                                FilterGroup(
                                    name = "Type",
                                    selected = state.characterFilter.type,
                                    labelList = state.typeLabelList,
                                    isListFinished = false,
                                    resolveIcon = characterTypeIconResolver,
                                    onSelectedChanged = onTypeSelected,
                                )
                            ),
                            onBackPress = onBackPress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                                .animateEnterExit(
                                    enter = slideInVertically { -it },
                                    exit = slideOutVertically { -it }
                                )
                                .background(color = MaterialTheme.colorScheme.background) //must be after renderInSharedTransitionScopeOverlay()
                        )
                    }
                },
                content = { paddingValues ->
                    val infiniteTransition =
                        rememberInfiniteTransition(label = "CharactersListScreen transition")

                    LazyVerticalStaggeredGrid(
                        state = rememberGridStateWithClearFocus(),
                        columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                        verticalItemSpacing = PADDING,
                        horizontalArrangement = Arrangement.spacedBy(PADDING),
                        contentPadding = PaddingValues(
                            top = paddingValues.calculateTopPadding(),
                            bottom = PADDING
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = PADDING)
                    ) {

                        val itemModifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.75f)

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

                    That's why pagingCharacterItems.loadState.append.endOfPaginationReached check was added.
                    */
                        if (pagingCharacterItems.loadState.refresh is LoadState.Loading
                            || (pagingCharacterItems.loadState.refresh is LoadState.NotLoading
                                    && pagingCharacterItems.itemCount == 0
                                    && !pagingCharacterItems.loadState.append.endOfPaginationReached)
                        ) {
                            items(20) {
                                CharacterCardPlaceholder(
                                    infiniteTransition = infiniteTransition,
                                    modifier = itemModifier
                                )
                            }
                        } else if (pagingCharacterItems.itemCount == 0 && pagingCharacterItems.loadState.append.endOfPaginationReached) {
                            item(span = FullLine) {
                                EmptyView(modifier = Modifier.fillMaxSize())
                            }
                        } else {
                            charactersListViewData(
                                pagingCharacterItems = pagingCharacterItems,
                                onCharacterItemClick = onCharacterItemClick,
                                itemModifier = itemModifier
                            )
                        }
                    }
                }
            )
        }
    }
}

fun LazyStaggeredGridScope.charactersListViewData(
    pagingCharacterItems: LazyPagingItems<Character>,
    onCharacterItemClick: (Int) -> Unit,
    itemModifier: Modifier
) {
    items(
        count = pagingCharacterItems.itemCount,
        key = pagingCharacterItems.itemKey { character -> character.id },
    ) { index ->
        pagingCharacterItems[index]?.let { character ->
            CharacterCard(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                origin = character.origin.name,
                imageUrl = character.image,
                isFavorite = false,
                onFavoriteClick = {},
                onCardClick = { onCharacterItemClick(character.id) },
                modifier = itemModifier
            )
        }
    }

    val stateItemModifier = Modifier.fillMaxWidth()

    if (pagingCharacterItems.loadState.append is LoadState.Loading) {
        item(span = FullLine) {
            LoadingView(
                modifier = stateItemModifier
            )
        }
    }
    if (pagingCharacterItems.loadState.refresh is LoadState.Error) {
        item(span = FullLine) {
            ErrorView(
                error = pagingCharacterItems.loadState.refresh.cast<LoadState.Error>().error,
                modifier = stateItemModifier
            ) {
                pagingCharacterItems.retry()
            }
        }
    }
    if (pagingCharacterItems.loadState.append is LoadState.Error) {
        item(span = FullLine) {
            ErrorView(
                error = pagingCharacterItems.loadState.append.cast<LoadState.Error>().error,
                modifier = stateItemModifier
            ) {
                pagingCharacterItems.retry()
            }
        }
    }
}

@ThemePreviews
@Composable
fun CharactersListViewPreview() {
    RnmTheme {
        val character1 = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            origin = LocationShort(
                id = 1,
                name = "Earth (C-137)",
            ),
            location = LocationShort(
                id = 20,
                name = "Earth (Replacement Dimension)",
            ),
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        )

        CharactersListView(
            state = CharactersListState(
                characterFilter = CharacterFilter(
                    name = "",
                    status = "",
                    species = "",
                ),
                characters = flowOf(
                    value = PagingData.from(
                        data = listOf(
                            character1,
                            character1.copy(id = 2)
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
            onStatusSelected = {},
            onSpeciesSelected = {},
            onGenderSelected = {},
            onTypeSelected = {},
            onCharacterItemClick = {},
            onBackPress = {},
        )
    }
}
