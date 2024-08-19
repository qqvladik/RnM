package pl.mankevich.characterslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import pl.mankevich.core.util.isOnline
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.model.Character
import pl.mankevich.model.Filter
import javax.inject.Inject

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class CharactersListViewModel
@Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private var instantRefresh = false
    private val searchQueryFlow = MutableStateFlow("")
    val displayedSearchQuery: Flow<String> = searchQueryFlow

    private val _isOnline = MutableStateFlow(true)
    val isOnline: Flow<Boolean> = _isOnline

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    internal val _characters = searchQueryFlow
        .debounce { if (instantRefresh) 0 else QUERY_INPUT_DELAY_MILLIS }
        .onEach { instantRefresh = false }
        .flatMapLatest {
            val isOnline = isOnline(Dispatchers.IO)
            _isOnline.tryEmit(isOnline)
            characterRepository.getCharactersPageFlow(isOnline, Filter(name = it))
        }

    val pagedData: Flow<PagingData<Character>> = _characters.cachedIn(viewModelScope)

    fun updateSearchQuery(newQuery: String) {
        searchQueryFlow.tryEmit(newQuery)
    }
}