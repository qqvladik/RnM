package pl.mankevich.databaseapi.dao

abstract class DaoBase {

    protected val tableUpdateNotifier = WeakNotifier()

    fun addTableUpdateWeakListener(onTableUpdateWeakListener: () -> Unit) {
        tableUpdateNotifier.addListener(onTableUpdateWeakListener)
    }
}