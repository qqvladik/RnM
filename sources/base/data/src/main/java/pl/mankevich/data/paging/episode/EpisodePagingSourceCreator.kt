package pl.mankevich.data.paging.episode

import dagger.assisted.AssistedFactory
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter
import javax.inject.Inject

class EpisodePagingSourceCreator @Inject constructor(
    private val episodePagingSourceCreatorOnline: EpisodePagingSourceCreatorOnline,
    private val episodePagingSourceCreatorOffline: EpisodePagingSourceCreatorOffline
) {

    fun create(isOnline: Boolean, episodeFilter: EpisodeFilter): PagingSource<Episode> {
        return if (isOnline) {
            episodePagingSourceCreatorOnline.create(episodeFilter)
        } else {
            episodePagingSourceCreatorOffline.create(episodeFilter)
        }
    }
}

@AssistedFactory
interface EpisodePagingSourceCreatorOffline {

    fun create(episodeFilter: EpisodeFilter): EpisodePagingSourceOffline
}

@AssistedFactory
interface EpisodePagingSourceCreatorOnline {

    fun create(episodeFilter: EpisodeFilter): EpisodePagingSourceOnline
}