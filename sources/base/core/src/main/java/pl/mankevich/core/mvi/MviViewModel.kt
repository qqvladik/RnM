package pl.mankevich.core.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

abstract class MviViewModel<in TIntent : Any, TStateWithEffects>(
    initialStateWithEffects: TStateWithEffects,
) : ViewModel() {

    private val transforms = MutableSharedFlow<Transform<TStateWithEffects>>()
    private val multimap = Multimap<TIntent, Job>()

    val stateWithEffects: StateFlow<TStateWithEffects> by lazy {
        transforms
            .scan(initialStateWithEffects, this::reduce)
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialStateWithEffects)
    }

    fun sendIntent(intent: TIntent) {
        val job = viewModelScope.launch {
            if (intent is UniqueIntent) {
                cleanIntentJobsOfType(intent::class)
            }

            try {
                transforms.emitAll(executeIntent(intent))
            } catch (throwable: Throwable) {
                if (viewModelScope.isActive && throwable !is TerminatedIntentException) {
                    Log.d("MVI", "Error while executing intent $intent: $throwable")
                }
            }
        }

        val entry = multimap.put(intent, job)
        job.invokeOnCompletion { multimap.remove(entry) }
    }

    private suspend fun <T : TIntent> cleanIntentJobsOfType(intentClass: KClass<T>) {
        val uniqueJob = coroutineContext[Job]
        multimap[intentClass].forEach {
            if (it.value != uniqueJob) {
                it.value.cancel(TerminatedIntentException())
            }
        }
    }

    private fun reduce(
        previousStateWithEffects: TStateWithEffects,
        transform: Transform<TStateWithEffects>,
    ): TStateWithEffects {
        return try {
            transform.reduce(previousStateWithEffects)
        } catch (throwable: Throwable) {
            Log.d("MVI", "Failed to reduce state with effects: $throwable")
            previousStateWithEffects
        }
    }

    protected abstract fun executeIntent(intent: TIntent): Flow<Transform<TStateWithEffects>>
}