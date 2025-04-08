package pl.mankevich.dataapi.dto

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character

data class CharactersResultDto(
    val isOnline: Boolean,
    val characters: Flow<PagingData<Character>>
)
