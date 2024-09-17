package pl.mankevich.domainapi.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character
import pl.mankevich.model.Filter

interface LoadCharactersListUseCase {

    suspend operator fun invoke(filter: Filter): Flow<PagingData<Character>>
}