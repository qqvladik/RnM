package pl.mankevich.dataapi.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character
import pl.mankevich.model.Filter

interface CharacterRepository {

    suspend fun getCharactersPageFlow(filter: Filter): Flow<PagingData<Character>>
}