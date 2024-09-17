package pl.mankevich.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import pl.mankevich.core.di.Dispatcher
import pl.mankevich.core.di.RnmDispatchers
import pl.mankevich.core.util.checkConnection
import javax.inject.Inject

class NetworkManager
@Inject constructor(
    @Dispatcher(RnmDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun isOnline(): Boolean =
        checkConnection(dispatcher)
}