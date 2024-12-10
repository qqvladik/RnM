package pl.mankevich.databaseapi.dao

interface Transaction {

    suspend operator fun <R> invoke(block: suspend () -> R): R
}