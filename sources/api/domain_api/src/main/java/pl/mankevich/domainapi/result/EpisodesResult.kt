package pl.mankevich.domainapi.result

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode

data class EpisodesResult(
    val isOnline: Boolean,
    val episodes: Flow<PagingData<Episode>>
)
