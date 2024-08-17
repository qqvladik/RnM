package pl.mankevich.network.datasource

fun String?.obtainId(): Int? {
    if (this.isNullOrBlank()) return null
    return this.substringAfterLast('/').toInt()
}