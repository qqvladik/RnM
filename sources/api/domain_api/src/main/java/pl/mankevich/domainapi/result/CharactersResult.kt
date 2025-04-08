package pl.mankevich.domainapi.result

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character

data class CharactersResult(
    val isOnline: Boolean,
    val characters: Flow<PagingData<Character>>
)
