package pl.mankevich.dataapi.dto

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Episode

data class EpisodesResultDto(
    val isOnline: Boolean,
    val episodes: Flow<PagingData<Episode>>
)
