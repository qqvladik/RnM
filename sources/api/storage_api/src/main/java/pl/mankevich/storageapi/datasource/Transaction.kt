package pl.mankevich.storageapi.datasource

interface Transaction {

    suspend operator fun <R> invoke(block: suspend () -> R): R
}