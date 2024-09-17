package pl.mankevich.core.mvi

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

/**
 * A thread-safe side effect container
 * It only returns each SideEffect once, if you use it as an [Iterable] it will emit each SideEffect and remove them so it's a perfect case for one shot SideEffects.
 * It locks itself, so you can't add and read at the same time, also it's not possible to read it at the same time from different threads, being completely thread-safe.
 */
class SideEffects<TSideEffect>() : Iterable<TSideEffect> {
    private val sideEffects: AtomicRef<MutableList<TSideEffect>> = atomic(ArrayList())

    // Private constructor to initialize from an Iterable
    private constructor(sideEffects: Iterable<TSideEffect>) : this() {
        this.sideEffects.value.addAll(sideEffects)
    }

    fun add(vararg sideEffectsToAdd: TSideEffect): SideEffects<TSideEffect> {
        val newList = sideEffects.value.toMutableList()
        newList.addAll(sideEffectsToAdd)
        return SideEffects(newList)
    }

    fun clear(): SideEffects<TSideEffect> {
        return SideEffects()
    }

    override fun iterator(): Iterator<TSideEffect> =
        iterator {
            while (true) {
                val currentList = sideEffects.value
                if (currentList.isEmpty()) break
                val nextSideEffect = currentList.removeFirstOrNull()
                nextSideEffect?.let { yield(it) }
            }
        }
}