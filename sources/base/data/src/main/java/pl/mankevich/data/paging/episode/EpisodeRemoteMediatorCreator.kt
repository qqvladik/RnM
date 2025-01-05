package pl.mankevich.data.paging.episode

import dagger.assisted.AssistedFactory
import pl.mankevich.model.EpisodeFilter
import javax.inject.Inject

class EpisodeRemoteMediatorCreator @Inject constructor(
    private val episodeRemoteMediatorFactoryOnline: EpisodeRemoteMediatorFactoryOnline
) {

    fun create(isOnline: Boolean, episodeFilter: EpisodeFilter): EpisodeRemoteMediator? {
        return if (isOnline) {
            episodeRemoteMediatorFactoryOnline.create(episodeFilter)
        } else {
            null
        }
    }
}

@AssistedFactory
interface EpisodeRemoteMediatorFactoryOnline {

    fun create(episodeFilter: EpisodeFilter): EpisodeRemoteMediator
}