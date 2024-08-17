package pl.mankevich.storageapi.dao

interface Transaction {

    suspend operator fun <R> invoke(block: suspend () -> R): R
}