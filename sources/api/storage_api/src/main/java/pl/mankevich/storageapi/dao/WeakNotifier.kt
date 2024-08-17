package pl.mankevich.storageapi.dao

import java.lang.ref.WeakReference

class WeakNotifier { //TODO think about moving to core or util module

    private val listeners = mutableListOf<WeakReference<() -> Unit>>()

    fun addListener(listener: () -> Unit) {
        listeners.add(WeakReference(listener))
    }

    fun notifyListeners() {
        val iterator = listeners.iterator()
        while (iterator.hasNext()) {
            val listenerRef = iterator.next()
            val listener = listenerRef.get()
            if (listener == null) {
                iterator.remove()
            } else {
                listener.invoke()
            }
        }
    }
}