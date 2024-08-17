package pl.mankevich.storageapi.datasource

abstract class StorageDataSourceBase { //TODO think about moving to core or util module

    protected val tableUpdateNotifier = WeakNotifier()

    fun addTableUpdateWeakListener(onTableUpdateWeakListener: () -> Unit) {
        tableUpdateNotifier.addListener(onTableUpdateWeakListener)
    }
}