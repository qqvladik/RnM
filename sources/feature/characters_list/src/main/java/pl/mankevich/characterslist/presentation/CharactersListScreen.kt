package pl.mankevich.characterslist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.Flow
import pl.mankevich.characterslist.presentation.view.EmptyView
import pl.mankevich.characterslist.presentation.view.ErrorView
import pl.mankevich.characterslist.presentation.view.LoadingView
import pl.mankevich.characterslist.presentation.view.SearchField

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel
) {
//    Text("CharactersList")
//}

    val pagingCharacterItems =
        rememberFlowWithLifecycle(viewModel.pagedData).collectAsLazyPagingItems()
    val query by rememberFlowWithLifecycle(viewModel.displayedSearchQuery).collectAsState(initial = "")

//    Column(modifier = modifier) {
    Column(modifier = Modifier) {
        SearchField(
            value = query, //TODO create filter
            onValueChange = {
                viewModel.updateSearchQuery(it)
            },
            onClearClick = { viewModel.updateSearchQuery("") },
            hint = "Search..."
        )

        if (pagingCharacterItems.loadState.refresh is LoadState.Loading) {
            LoadingView()
        } else if (pagingCharacterItems.itemCount == 0) {
            EmptyView()
        } else {
            LazyColumn( //TODO add savePosition
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val stateItemModifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

                if (pagingCharacterItems.loadState.prepend is LoadState.Error) {
                    item {
                        ErrorView(
                            error = pagingCharacterItems.loadState.prepend.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }

                if (pagingCharacterItems.loadState.prepend is LoadState.Loading) {
                    item {
                        LoadingView(
                            modifier = stateItemModifier
                        )
                    }
                }
                items(
                    count = pagingCharacterItems.itemCount,
                    key = pagingCharacterItems.itemKey { character -> character.id }
                ) { index ->
                    pagingCharacterItems[index]?.let { character ->
                        Text(
                            character.id.toString() + " " + character.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                                .clickable {
//                                    navController.navigate("rnm/characters/${item.id}")
                                }
                        )
                    }
                }

                if (pagingCharacterItems.loadState.append is LoadState.Loading) {
                    item {
                        LoadingView(
                            modifier = stateItemModifier
                        )
                    }
                }
                if (pagingCharacterItems.loadState.refresh is LoadState.Error) {
                    item {
                        ErrorView(
                            error = pagingCharacterItems.loadState.refresh.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }
                if (pagingCharacterItems.loadState.append is LoadState.Error) {
                    item {
                        ErrorView(
                            error = pagingCharacterItems.loadState.append.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@Composable
fun <T> rememberSavableFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = rememberSaveable(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}