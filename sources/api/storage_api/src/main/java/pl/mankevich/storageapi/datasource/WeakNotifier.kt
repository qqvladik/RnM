package pl.mankevich.storageapi.datasource

import java.lang.ref.WeakReference

class WeakNotifier {

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